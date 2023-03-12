document.addEventListener("DOMContentLoaded", function(){
    document.querySelectorAll(".btn.btn-primary").forEach(function(button){
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

document.addEventListener("DOMContentLoaded", function(){
    // Fetch home.html content when page loads
    fetch("home.html")
        .then(response => response.text())
        .then(result => {
            document.querySelector("#content").innerHTML = result;
        });
});


document.addEventListener("DOMContentLoaded", function(){
    document.querySelector("a[href='login.html']").addEventListener("click", function(e){
        e.preventDefault();
        fetch("login.html")
            .then(response => response.text())
            .then(result => {
                document.querySelector("#content").innerHTML = result;
            });
    });
});