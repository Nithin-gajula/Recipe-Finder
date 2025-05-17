document.getElementById('contactForm').addEventListener('submit', function (e) {
  e.preventDefault();

  const name = document.getElementById('name').value.trim();
  const email = document.getElementById('email').value.trim();
  const message = document.getElementById('message').value.trim();
  const formMessage = document.getElementById('formMessage');

  // Simple Validation
  if (!name || !email || !message) {
    formMessage.textContent = 'Please fill out all fields.';
    formMessage.className = 'form-message error';
    return;
  }

  // Simulate form submission (API or backend integration can replace this)
  setTimeout(() => {
    formMessage.textContent = 'Thank you! Your message has been sent.';
    formMessage.className = 'form-message success';

    // Clear form
    document.getElementById('contactForm').reset();
  }, 1000);
});
