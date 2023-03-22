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
                document.querySelector("button[data-target='/templates/allMemberships.html']").style.display = "none";
                document.querySelector("#my-perks").style.display = "none";
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
            let contentTarget;

            // Check which button was clicked and set the appropriate content target
            if (target === '/templates/allMemberships.html') {
                contentTarget = '/myMemberships-content';
            } else if (target === '/templates/allPerks.html') {
                contentTarget = '/all-perks-content';
            } else if (this.innerHTML == 'My Perks'){
                contentTarget = '/my-perks-content';
            } else {
                contentTarget = target;
            }

            // Fetch updated content from the specified content target
            fetch(contentTarget)
                .then(response => response.text())
                .then(content => {
                    document.querySelector('#content').innerHTML = content;
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

