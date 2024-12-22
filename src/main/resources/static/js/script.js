console.log("loaded");

let currenttheme = getTheme();// Get the current theme from localStorage
document.addEventListener("DOMContentLoaded",()=>{
    changeTheme(); // Initialize the theme

});

function changeTheme() {
    document.querySelector('html').classList.add(currenttheme); // Apply the current theme to <html>
    const changeThemeButton = document.querySelector('#theme_change_button');
    changeThemeButton.querySelector('span').textContent = currenttheme === "light" ? "Dark" : "Light";

    // Attach a click event listener
    changeThemeButton.addEventListener("click", () => {
        console.log("click");
        document.querySelector('html').classList.remove(currenttheme); // Remove the current theme class
        currenttheme = currenttheme === "dark" ? "light" : "dark"; // Toggle theme
        setTheme(currenttheme); // Save the new theme in localStorage
        document.querySelector('html').classList.add(currenttheme); // Apply the new theme
        changeThemeButton.querySelector('span').textContent = currenttheme === "light" ? "Dark" : "Light"; // Update button text
    });
}

function setTheme(theme) {
    localStorage.setItem("theme", theme); // Save the theme in localStorage
}

function getTheme() {
    let theme = localStorage.getItem("theme"); // Get the theme from localStorage
    return theme ? theme : "light"; // Default to "light" if no theme is found
}