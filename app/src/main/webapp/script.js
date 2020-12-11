$(function () {
    const navs = $(".side-bar li:not(:first-child)")
    const container = $(".content")

    container.html(pages[navs[0].textContent])

    navs.click(x => container.html(pages[x.target.textContent]))

    function removeUser (user) {
        console.log(user)
    }
})