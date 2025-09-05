document.addEventListener('DOMContentLoaded', () => {
    const foodChoicesContainer = document.querySelector('.food-choices');
    const predictButton = document.getElementById('predictButton');
    const predictionResult = document.getElementById('predictionResult');
    const genderSelect = document.getElementById('gender');
    const ageInput = document.getElementById('age');

    // Define food items with hidden toxic and energy levels
    // Toxic level reduces years, Energy level adds years
    const foodItems = [
        { name: 'Broccoli', toxic: -0.5, energy: 2.0, img: 'https://images.unsplash.com/photo-1653576840776-47a12be506e4?q=80&w=387&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D' },
        { name: 'Burger', toxic: 3.0, energy: -1.0, img: 'https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGJ1cmdlcnxlbnwwfHwwfHx8MA%3D%3D' },
        { name: 'Salad', toxic: -1.0, energy: 1.5, img: 'https://images.unsplash.com/photo-1623428187969-5da2dcea5ebf?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTZ8fHNhbGFkfGVufDB8fDB8fHww' },
        { name: 'Soda', toxic: 2.5, energy: -1.5, img: 'https://images.unsplash.com/photo-1624552184280-9e9631bbeee9?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8c29kYXxlbnwwfHwwfHx8MA%3D%3D' },
        { name: 'Pizza', toxic: 2.0, energy: -0.8, img: 'https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8cGl6emF8ZW58MHx8MHx8fDA%3D' },
        { name: 'Fish', toxic: -0.8, energy: 1.8, img: 'https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Z3JpbGxlZCUyMHNhbG1vbnxlbnwwfHwwfHx8MA%3D%3D' },
        { name: 'Donut', toxic: 1.8, energy: -0.5, img: 'https://images.unsplash.com/photo-1527515545081-5db817172677?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTJ8fGRvbnV0c3xlbnwwfHwwfHx8MA%3D%3D' },
        { name: 'Water', toxic: -1.5, energy: 2.5, img: 'https://media.istockphoto.com/id/2184942460/photo/hand-holding-drinking-water-bottle-and-pouring-water-into-glass-on-wooden-table-on-blurred.webp?a=1&b=1&s=612x612&w=0&k=20&c=-w6bwRbxlKLzJtEyaQHH00Exvzp5qZcRywrOdAoe_ys=' },
        { name: 'Fries', toxic: 2.2, energy: -0.7, img: 'https://images.unsplash.com/photo-1598998834333-c0b91bc9b2a3?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8ZnJpZXN8ZW58MHx8MHx8fDA%3D' },
        { name: 'Apple', toxic: -0.7, energy: 1.2, img: 'https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8YXBwbGV8ZW58MHx8MHx8fDA%3D' }
    ];

    let selectedFood = [];

    // Dynamically create food items
    function renderFoodChoices() {
        foodChoicesContainer.innerHTML = '';
        foodItems.forEach(food => {
            const foodDiv = document.createElement('div');
            foodDiv.classList.add('food-item');
            foodDiv.dataset.name = food.name;
            foodDiv.innerHTML = `
                <img src="${food.img}" alt="${food.name}">
                <span>${food.name}</span>
            `;
            foodDiv.addEventListener('click', () => toggleFoodSelection(foodDiv, food));
            foodChoicesContainer.appendChild(foodDiv);
        });
    }

    function toggleFoodSelection(foodDiv, food) {
        if (foodDiv.classList.contains('selected')) {
            foodDiv.classList.remove('selected');
            selectedFood = selectedFood.filter(item => item.name !== food.name);
        } else {
            if (selectedFood.length < 5) { // Limit to 5 selections
                foodDiv.classList.add('selected');
                selectedFood.push(food);
            } else {
                alert('You can only select a maximum of 5 food items.');
            }
        }
        // console.log(selectedFood); // For debugging
    }

    // Mathematical calculation to predict years left
    function predictYearsLeft(gender, age, diet) {
        let years = 80; // Base lifespan expectation (can be adjusted)

        // Adjust for age and gender (simplified)
        if (gender === 'male') {
            years -= 5; // Men statistically live slightly shorter
        } else if (gender === 'female') {
            years += 2; // Women statistically live slightly longer
        }
        
        years -= (age * 0.5); // Older people have fewer years left, obviously. Factor can be adjusted.

        // Adjust based on food choices
        diet.forEach(food => {
            years += food.energy;
            years -= food.toxic;
        });

        // Ensure years don't go below 0 or ridiculously high
        years = Math.max(0, years);
        years = Math.min(100, years); // Cap for realism

        return Math.round(years);
    }

    predictButton.addEventListener('click', () => {
        const gender = genderSelect.value;
        const age = parseInt(ageInput.value);

        if (selectedFood.length < 4 || selectedFood.length > 5) {
            predictionResult.style.color = '#ff9933'; // Orange for warnings
            predictionResult.textContent = 'Please select between 4 and 5 food items.';
            return;
        }

        if (isNaN(age) || age < 1 || age > 120) {
            predictionResult.style.color = '#ff9933'; // Orange for warnings
            predictionResult.textContent = 'Please enter a valid age (1-120).';
            return;
        }

        const predictedYears = predictYearsLeft(gender, age, selectedFood);

        if (predictedYears <= 0) {
            predictionResult.style.color = '#ff4d4d'; // Red for immediate doom
            predictionResult.textContent = `Oh dear. You've already expired, or perhaps have mere moments left.`;
        } else if (predictedYears < 5) {
            predictionResult.style.color = '#ff4d4d';
            predictionResult.textContent = `Grave news! You have approximately ${predictedYears} year(s) until your demise.`;
        } else if (predictedYears < 15) {
            predictionResult.style.color = '#ff9933'; // Orange for warning
            predictionResult.textContent = `The sands of time are running low. You have about ${predictedYears} years left.`;
        } else {
            predictionResult.style.color = '#33ff33'; // Green for good news
            predictionResult.textContent = `A long road ahead! You have approximately ${predictedYears} years until your final curtain call.`;
        }
    });

    // Initial render
    renderFoodChoices();
});