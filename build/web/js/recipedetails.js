// Simulated data (replace with API call if needed)
const recipeId = new URLSearchParams(window.location.search).get('id');

// Fetch Recipe Details
const fetchRecipeDetails = async () => {
  try {
    // Example API endpoint (replace with your own API)
    const response = await fetch(`https://api.example.com/recipes/${recipeId}`);
    const data = await response.json();

    // Populate the recipe details
    document.getElementById('recipeTitle').textContent = data.title;
    document.getElementById('recipeImage').src = data.image;
    document.getElementById('instructions').textContent = data.instructions;

    const ingredientsList = document.getElementById('ingredientsList');
    data.ingredients.forEach((ingredient) => {
      const li = document.createElement('li');
      li.textContent = ingredient;
      ingredientsList.appendChild(li);
    });
  } catch (error) {
    console.error('Error fetching recipe details:', error);
    document.getElementById('recipeTitle').textContent = 'Error loading recipe details.';
  }
};

// Execute on page load
document.addEventListener('DOMContentLoaded', fetchRecipeDetails);
