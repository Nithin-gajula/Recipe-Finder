// Sample favorite recipes data (replace with localStorage or API calls)
const favoriteRecipes = [
  {
    id: 1,
    title: 'Spaghetti Carbonara',
    image: 'https://via.placeholder.com/200',
  },
  {
    id: 2,
    title: 'Classic Cheesecake',
    image: 'https://via.placeholder.com/200',
  },
];

// Populate favorite recipes on page load
document.addEventListener('DOMContentLoaded', () => {
  const favoritesGrid = document.getElementById('favoritesGrid');
  const emptyMessage = document.getElementById('emptyMessage');

  // Check if there are any favorites
  if (favoriteRecipes.length === 0) {
    emptyMessage.style.display = 'block';
  } else {
    emptyMessage.style.display = 'none';
    favoriteRecipes.forEach((recipe) => {
      const recipeCard = document.createElement('div');
      recipeCard.className = 'favorite-card';
      recipeCard.innerHTML = `
        <img src="${recipe.image}" alt="${recipe.title}">
        <h3>${recipe.title}</h3>
        <button class="remove-btn" data-id="${recipe.id}">&times;</button>
      `;
      favoritesGrid.appendChild(recipeCard);
    });
  }
});

// Remove recipe from favorites
document.getElementById('favoritesGrid').addEventListener('click', (e) => {
  if (e.target.classList.contains('remove-btn')) {
    const recipeId = parseInt(e.target.getAttribute('data-id'), 10);
    // Find the recipe index and remove it
    const recipeIndex = favoriteRecipes.findIndex((recipe) => recipe.id === recipeId);
    if (recipeIndex !== -1) {
      favoriteRecipes.splice(recipeIndex, 1);
      // Re-render favorites
      e.target.parentElement.remove();
    }
  }

  // Show empty message if no favorites remain
  if (favoriteRecipes.length === 0) {
    document.getElementById('emptyMessage').style.display = 'block';
  }
});
