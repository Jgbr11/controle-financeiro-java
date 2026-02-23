// ===== GASTOS PAGE - CRUD OPERATIONS =====
document.addEventListener('DOMContentLoaded', function () {

    const modal = document.getElementById('modalGasto');
    const form = document.getElementById('formGasto');
    const modalTitle = document.getElementById('modalTitle');
    const bsModal = modal ? new bootstrap.Modal(modal) : null;

    // ===== CREATE / UPDATE =====
    if (form) {
        form.addEventListener('submit', function (e) {
            e.preventDefault();
            const id = document.getElementById('gastoId').value;
            const payload = {
                descricao: document.getElementById('descricao').value,
                valor: parseFloat(document.getElementById('valor').value),
                categoria: document.getElementById('categoria').value,
                data: document.getElementById('data').value
            };

            const url = id ? '/api/gastos/' + id : '/api/gastos';
            const method = id ? 'PUT' : 'POST';

            // Get CSRF token from cookie if present
            const csrfMeta = document.querySelector('meta[name="_csrf"]');
            const headers = { 'Content-Type': 'application/json' };
            if (csrfMeta) headers['X-CSRF-TOKEN'] = csrfMeta.content;

            fetch(url, { method, headers, body: JSON.stringify(payload), credentials: 'same-origin' })
                .then(res => {
                    if (res.ok) {
                        bsModal.hide();
                        showAlert(id ? 'Gasto atualizado com sucesso!' : 'Gasto cadastrado com sucesso!');
                        setTimeout(() => location.reload(), 800);
                    } else {
                        res.json().then(data => alert('Erro: ' + JSON.stringify(data)));
                    }
                })
                .catch(err => alert('Erro de conexão: ' + err));
        });
    }

    // ===== EDIT BUTTON =====
    document.querySelectorAll('.btn-edit').forEach(btn => {
        btn.addEventListener('click', function () {
            document.getElementById('gastoId').value = this.dataset.id;
            document.getElementById('descricao').value = this.dataset.descricao;
            document.getElementById('valor').value = this.dataset.valor;
            document.getElementById('categoria').value = this.dataset.categoria;
            document.getElementById('data').value = this.dataset.data;
            modalTitle.innerHTML = '<i class="bi bi-pencil me-2 text-warning"></i>Editar Gasto';
            bsModal.show();
        });
    });

    // ===== DELETE BUTTON =====
    document.querySelectorAll('.btn-del').forEach(btn => {
        btn.addEventListener('click', function () {
            if (!confirm('Tem certeza que deseja excluir este gasto?')) return;
            const id = this.dataset.id;
            fetch('/api/gastos/' + id, { method: 'DELETE', credentials: 'same-origin' })
                .then(res => {
                    if (res.ok || res.status === 204) {
                        showAlert('Gasto excluído com sucesso!');
                        setTimeout(() => location.reload(), 800);
                    }
                })
                .catch(err => alert('Erro: ' + err));
        });
    });

    // ===== RESET MODAL ON OPEN =====
    if (modal) {
        modal.addEventListener('show.bs.modal', function (e) {
            if (e.relatedTarget && e.relatedTarget.classList.contains('btn-success')) {
                form.reset();
                document.getElementById('gastoId').value = '';
                modalTitle.innerHTML = '<i class="bi bi-plus-circle me-2 text-success"></i>Novo Gasto';
                document.getElementById('data').valueAsDate = new Date();
            }
        });
    }

    function showAlert(msg) {
        const el = document.getElementById('alertSuccess');
        const msgEl = document.getElementById('alertMsg');
        if (el && msgEl) {
            msgEl.textContent = msg;
            el.classList.remove('d-none');
            el.classList.add('show');
        }
    }
});
