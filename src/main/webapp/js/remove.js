async function removeAccount(email) {
    try {
        const resp = await fetch(`/userCtrl/remove?email=${email}`);
        return await resp.text();
    } catch (error) {
        console.log(error);
    }
}

document.getElementById("del").addEventListener("click", () => {
    console.log(document.getElementById("email").value);
    removeAccount(document.getElementById("email").value).then(result => {
        alert("계정이 삭제되었습니다.");
        location.href = "http://localhost:8081/";
    });
});