//see if the user is logged in or not
document.addEventListener("DOMContentLoaded", function(){
    fetch("/auth-status")
        .then(response => response.json())
        .then(data => {
            if (data.loggedIn) {
                // If the user is logged in, show the "My Membership" button and change the "Login" button to a "Logout" button
                document.querySelector("button[data-target='userMembership.html']").style.display = "inline-block";
                let loginButton = document.querySelector("a[href='login.html']");
                loginButton.textContent = "Logout";
                loginButton.setAttribute("href", "logout.html");
            } else {
                // If the user is not logged in, hide the "My Membership" button
                document.querySelector("button[data-target='userMembership.html']").style.display = "none";
            }
        });
});

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
    fetch("home.html")
        .then(response => response.text())
        .then(result => {
            document.querySelector("#content").innerHTML = result;
        });
});
