/* Sprint 1 : UX de démonstration uniquement. Aucun identifiant envoyé au serveur. */
document.addEventListener('DOMContentLoaded', () => {
  const loginForm = document.getElementById('ndy-login-form');
  const feedback = document.getElementById('ndy-login-feedback');
  if (loginForm && feedback) {
    loginForm.addEventListener('submit', (event) => {
      event.preventDefault();
      feedback.textContent = 'Démonstration : la connexion ne sera fonctionnelle qu’au Sprint 2.';
    });
  }
  const toggle = document.getElementById('ndy-toggle-password');
  const password = document.getElementById('ndy-password');
  if (toggle && password) {
    toggle.addEventListener('click', () => {
      const wasHidden = password.type === 'password';
      password.type = wasHidden ? 'text' : 'password';
      toggle.textContent = wasHidden ? 'Masquer' : 'Afficher';
      toggle.setAttribute('aria-label', wasHidden ? 'Masquer le mot de passe' : 'Afficher le mot de passe');
    });
  }
});


/* S2 : Markdown -> HTML assaini. Ne jamais insérer le HTML brut de l'IA. */
document.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.markdown-content.assistant-message').forEach(element => {
    // Si scripts CDN bloqués, th:text garde un affichage texte sans XSS.
    if (!window.marked || !window.DOMPurify) return;
    const rawMarkdown = element.textContent || '';
    try {
      const unsafeHtml = window.marked.parse(rawMarkdown, { breaks: true });
      const safeHtml = window.DOMPurify.sanitize(unsafeHtml, { USE_PROFILES: { html: true } });
      element.innerHTML = safeHtml;
      element.classList.add('markdown-rendered');
      // Les liens externes ne doivent pas pouvoir accéder à window.opener.
      element.querySelectorAll('a[href]').forEach(link => {
        link.setAttribute('rel', 'noopener noreferrer nofollow');
      });
    } catch (_) { /* Reste en texte échappé. */ }
  });
});
