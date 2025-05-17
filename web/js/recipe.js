const searchButton = document.getElementById('searchButton');
const searchInput = document.getElementById('searchInput');
const recipesGrid = document.getElementById('recipesGrid');

const API_KEY = 'YOUR_API_KEY';

// Fetch recipes from API and display
async function fetchRecipes(query) {
  try {
    const response = await fetch(`https://api.spoonacular.com/recipes/complexSearch?query=${query}&number=9&apiKey=${API_KEY}`);
    const data = await response.json();

    // Clear previous results
    recipesGrid.innerHTML = '';

    if (data.results.length === 0) {
      recipesGrid.innerHTML = '<p>No recipes found. Try another search!</p>';
      return;
    }

    // Display recipe cards
    data.results.forEach(recipe => {
      const recipeCard = document.createElement('div');
      recipeCard.className = 'recipe-card';
      recipeCard.innerHTML = `
        <img src="${recipe.image}" alt="${recipe.title}">
        <h3>${recipe.title}</h3>
        <p>${recipe.summary || 'A delicious recipe to try!'}</p>
        <a href="https://spoonacular.com/recipes/${recipe.title}-${recipe.id}" target="_blank" class="view-recipe-btn">View Recipe</a>
      `;
      recipesGrid.appendChild(recipeCard);
    });
  } catch (error) {
    console.error('Error fetching recipes:', error);
    recipesGrid.innerHTML = '<p>Something went wrong. Please try again later.</p>';
  }
}

// Handle search button click
searchButton.addEventListener('click', () => {
  const query = searchInput.value.trim();
  if (query) {
    fetchRecipes(query);
  } else {
    alert('Please enter a search term!');
  }
});
