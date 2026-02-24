package com.controle.financeiro.service;

import com.controle.financeiro.dto.GastoFixoDTO;
import com.controle.financeiro.model.Gasto;
import com.controle.financeiro.model.GastoFixo;
import com.controle.financeiro.model.User;
import com.controle.financeiro.repository.GastoFixoRepository;
import com.controle.financeiro.repository.GastoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GastoFixoService {

    private final GastoFixoRepository gastoFixoRepo;
    private final GastoRepository gastoRepo;

    public List<GastoFixo> listarTodos(Long usuarioId) {
        return gastoFixoRepo.findByUsuarioId(usuarioId);
    }

    public GastoFixo salvar(GastoFixoDTO dto, User usuario) {
        GastoFixo gf = GastoFixo.builder()
                .descricao(dto.descricao())
                .valor(dto.valor())
                .categoria(dto.categoria())
                .diaVencimento(dto.diaVencimento())
                .ativo(true)
                .usuario(usuario)
                .build();
        return gastoFixoRepo.save(gf);
    }

    public GastoFixo atualizar(Long id, GastoFixoDTO dto, Long usuarioId) {
        GastoFixo gf = gastoFixoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto fixo não encontrado"));
        if (!gf.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Gasto fixo não pertence ao usuário");
        }
        gf.setDescricao(dto.descricao());
        gf.setValor(dto.valor());
        gf.setCategoria(dto.categoria());
        gf.setDiaVencimento(dto.diaVencimento());
        gf.setAtivo(dto.ativo());
        return gastoFixoRepo.save(gf);
    }

    public void deletar(Long id, Long usuarioId) {
        GastoFixo gf = gastoFixoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto fixo não encontrado"));
        if (!gf.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("Gasto fixo não pertence ao usuário");
        }
        gastoFixoRepo.delete(gf);
    }

    /**
     * Lança os gastos fixos ativos do mês atual como gastos normais,
     * caso ainda não tenham sido lançados.
     */
    public void lancarGastosFixosDoMes(User usuario) {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate fimMes = hoje.with(TemporalAdjusters.lastDayOfMonth());

        List<GastoFixo> ativos = gastoFixoRepo.findByUsuarioIdAndAtivoTrue(usuario.getId());

        for (GastoFixo gf : ativos) {
            // Verifica se já foi lançado neste mês
            boolean jaLancado = gastoRepo.existsByUsuarioIdAndDescricaoAndCategoriaAndDataBetween(
                    usuario.getId(), gf.getDescricao(), gf.getCategoria(), inicioMes, fimMes);

            if (!jaLancado) {
                // Calcula a data: usa o dia de vencimento, ajustando para o último dia do mês
                // se necessário
                int diaMax = fimMes.getDayOfMonth();
                int dia = Math.min(gf.getDiaVencimento(), diaMax);
                LocalDate dataGasto = LocalDate.of(hoje.getYear(), hoje.getMonth(), dia);

                Gasto gasto = Gasto.builder()
                        .descricao(gf.getDescricao())
                        .valor(gf.getValor())
                        .categoria(gf.getCategoria())
                        .data(dataGasto)
                        .usuario(usuario)
                        .build();
                gastoRepo.save(gasto);
            }
        }
    }
}
