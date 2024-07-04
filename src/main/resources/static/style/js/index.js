document.getElementById('lang-set').addEventListener('click', function() {
    var togglelist = document.getElementById('language-list');

    if (togglelist.style.display !== 'none') {
        togglelist.style.display = 'none';
    } else {
        togglelist.style.display = 'block';
    }
})