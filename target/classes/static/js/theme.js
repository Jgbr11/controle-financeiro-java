// Alternância de tema claro/escuro
(function () {
    const THEME_KEY = 'cf-theme';

    // Retorna o tema salvo ou a preferência do sistema
    function getPreferred() {
        const saved = localStorage.getItem(THEME_KEY);
        if (saved) return saved;
        return window.matchMedia('(prefers-color-scheme: light)').matches ? 'light' : 'dark';
    }

    // Aplica o tema no documento e atualiza os elementos visuais
    function applyTheme(theme) {
        document.documentElement.setAttribute('data-theme', theme);
        localStorage.setItem(THEME_KEY, theme);

        // Atualiza o ícone do botão de alternância
        const btn = document.getElementById('btnThemeToggle');
        if (btn) {
            const icon = btn.querySelector('i');
            if (icon) {
                icon.className = theme === 'dark'
                    ? 'bi bi-sun-fill'
                    : 'bi bi-moon-fill';
            }
            btn.title = theme === 'dark' ? 'Mudar para tema claro' : 'Mudar para tema escuro';
        }

        // Atualiza as classes da tabela do Bootstrap
        document.querySelectorAll('.table-dark').forEach(function (el) {
            if (theme === 'light') {
                el.classList.remove('table-dark');
                el.classList.add('table-light', 'table-themed');
            }
        });
        document.querySelectorAll('.table-themed').forEach(function (el) {
            if (theme === 'dark') {
                el.classList.remove('table-light', 'table-themed');
                el.classList.add('table-dark');
            }
        });

        // Atualiza as classes da navbar
        const nav = document.querySelector('.navbar');
        if (nav) {
            if (theme === 'light') {
                nav.classList.remove('navbar-dark');
                nav.classList.add('navbar-light');
            } else {
                nav.classList.remove('navbar-light');
                nav.classList.add('navbar-dark');
            }
        }

        // Atualiza as cores padrão do Chart.js
        if (typeof Chart !== 'undefined') {
            Chart.defaults.color = theme === 'dark' ? '#8b949e' : '#57606a';
            Chart.defaults.borderColor = theme === 'dark' ? '#30363d' : '#d0d7de';
        }
    }

    // Aplica o tema imediatamente para evitar flash de tela
    applyTheme(getPreferred());

    document.addEventListener('DOMContentLoaded', function () {
        // Reaplica após o DOM estar pronto para atualizar botões e tabelas
        applyTheme(getPreferred());

        var btn = document.getElementById('btnThemeToggle');
        if (btn) {
            btn.addEventListener('click', function () {
                var current = document.documentElement.getAttribute('data-theme') || 'dark';
                applyTheme(current === 'dark' ? 'light' : 'dark');
            });
        }
    });
})();
