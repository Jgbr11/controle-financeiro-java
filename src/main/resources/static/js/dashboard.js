// ===== CHART.JS CONFIG =====
Chart.defaults.color = '#8b949e';
Chart.defaults.borderColor = '#30363d';
Chart.defaults.font.family = "'Segoe UI', system-ui, sans-serif";

const COLORS = ['#3fb950','#58a6ff','#d29922','#f85149','#bc8cff','#79c0ff','#56d364','#e3b341'];

// ===== PIE CHART - CATEGORIAS =====
(function() {
    const ctx = document.getElementById('chartCategoria');
    if (!ctx || !categoriaData) return;
    const labels = Object.keys(categoriaData);
    const data = Object.values(categoriaData);
    if (labels.length === 0) {
        ctx.parentElement.innerHTML = '<p class="text-center text-secondary py-5">Sem dados para exibir</p>';
        return;
    }
    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: labels,
            datasets: [{
                data: data,
                backgroundColor: COLORS.slice(0, labels.length),
                borderWidth: 0,
                hoverOffset: 8
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'bottom', labels: { padding: 15, usePointStyle: true } }
            }
        }
    });
})();

// ===== BAR CHART - MENSAL =====
(function() {
    const ctx = document.getElementById('chartMensal');
    if (!ctx || !resumoMensal) return;
    const labels = resumoMensal.map(r => r.label);
    const data = resumoMensal.map(r => r.total);
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Total (R$)',
                data: data,
                backgroundColor: 'rgba(88, 166, 255, 0.6)',
                borderColor: '#58a6ff',
                borderWidth: 1,
                borderRadius: 6,
                barPercentage: 0.6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { beginAtZero: true, grid: { color: '#21262d' }, ticks: { callback: v => 'R$ ' + v } },
                x: { grid: { display: false } }
            }
        }
    });
})();

// ===== LINE CHART - SEMANAL =====
(function() {
    const ctx = document.getElementById('chartSemanal');
    if (!ctx || !resumoSemanal) return;
    const labels = resumoSemanal.map(r => r.label);
    const data = resumoSemanal.map(r => r.total);
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Total (R$)',
                data: data,
                borderColor: '#d29922',
                backgroundColor: 'rgba(210, 153, 34, 0.1)',
                fill: true,
                tension: 0.4,
                pointRadius: 6,
                pointBackgroundColor: '#d29922',
                pointBorderColor: '#0d1117',
                pointBorderWidth: 2,
                pointHoverRadius: 8
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: { legend: { display: false } },
            scales: {
                y: { beginAtZero: true, grid: { color: '#21262d' }, ticks: { callback: v => 'R$ ' + v } },
                x: { grid: { display: false } }
            }
        }
    });
})();
