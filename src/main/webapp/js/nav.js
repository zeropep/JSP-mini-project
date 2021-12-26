const search = async (query) => {
  const res = await fetch(`/postCtrl/list?query=${query}`);
};

document.querySelector("#search-field").addEventListener("keydown", (e) => {
  if (e.key == "Enter") {
    console.log(e.target.value);
    search(e.target.value);
  }
});
