//see if the user is logged in or not
document.addEventListener("DOMContentLoaded", function(){
    fetch("/auth-status")
        .then(response => response.json())
        .then(data => {
            if (data.loggedIn) {
                // If the user is logged in, create a new element with the welcome message and the user's name
                let welcomeMessage = document.createElement("span");
                welcomeMessage.textContent = "Welcome, " + data.userName;

                // Create a logout button element
                let logoutButton = document.createElement("button");
                logoutButton.textContent = "Logout";
                logoutButton.setAttribute("data-target", "/templates/logout.html");
                logoutButton.setAttribute("class", "btn btn-light");
                logoutButton.addEventListener("click", function() {
                    window.location.href = "/logout";
                });

                // Create a container element to hold the welcome message and logout button
                let userContainer = document.createElement("div");
                userContainer.setAttribute("class", "d-flex justify-content-between align-items-center");
                userContainer.appendChild(welcomeMessage);
                userContainer.appendChild(logoutButton);

                // Replace the login button with the container element
                let loginButton = document.querySelector("button[data-target='/templates/login.html']");
                if (loginButton) {
                    loginButton.parentNode.replaceChild(userContainer, loginButton);
                }
            }else {
                // If the user is not logged in, hide the "My Membership" button
                document.querySelector("button[data-target='/templates/userMembership.html']").style.display = "none";
                document.querySelector("button[data-target='/templates/login.html']").addEventListener("click", function() {
                    window.location.href = "/login";});
            }
        });
});

document.addEventListener("DOMContentLoaded", function(){
    document.querySelectorAll(".btn").forEach(function(button){
        button.addEventListener("click", function(e){
            e.preventDefault();
            let target = this.getAttribute("data-target");
            // If the "My Membership" button is clicked
            if (target === '/templates/userMembership.html') {
                // Fetch updated content from /my-memberships-content
                fetch('/my-memberships-content')
                    .then(response => response.text())
                    .then(content => {
                        document.querySelector('#content').innerHTML = content;
                    });
            } else {
                // For other buttons, fetch content as before
                fetch(target)
                    .then(response => response.text())
                    .then(result => {
                        document.querySelector("#content").innerHTML = result;
                    });
            }
        });
    });
})

//update contents of the landing page based on which button is pressed
document.addEventListener("DOMContentLoaded", function(){
    document.querySelectorAll(".btn").forEach(function(button){
        button.addEventListener("click", function(e){
            e.preventDefault();
            let target = this.getAttribute("data-target");
            fetch(target)
                .then(response => response.text())
                .then(result => {
                    document.querySelector("#content").innerHTML = result;
                });
        });
    });
});

//have the home.html loaded by default
document.addEventListener("DOMContentLoaded", function(){
    // Fetch home.html content when page loads
    fetch("/templates/home.html")
        .then(response => response.text())
        .then(result => {
            document.querySelector("#content").innerHTML = result;
        });
});

