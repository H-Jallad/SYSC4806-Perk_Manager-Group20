//see if the user is logged in or not
document.addEventListener("DOMContentLoaded", function(){
    fetch("/auth-status")
        .then(response => response.json())
        .then(data => {
            if (data.loggedIn) {
                // If the user is logged in, show the "My Membership" button and change the "Login" button to a "Logout" button
                document.querySelector("button[data-target='/templates/userMembership.html']").style.display = "inline-block";
                let loginButton = document.querySelector("a[href='/templates/login.html']");
                loginButton.textContent = "Logout";
                loginButton.setAttribute("href", "/templates/logout.html");
            } else {
                // If the user is not logged in, hide the "My Membership" button
                //document.querySelector("button[data-target='/templates/userMembership.html']").style.display = "none";
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
            if (target === '/templates/userMembership.html') {
                contentTarget = '/my-memberships-content';
            } else if (target === '/templates/allPerks.html') {
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

