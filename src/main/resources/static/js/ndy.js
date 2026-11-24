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
