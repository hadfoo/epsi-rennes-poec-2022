/**
 On sélectionne les éléments avec un attribut particulier (a[title])
 Lorsque l'on survole un élément {
  On crée une <diV> dans le <body>
  On remplit la <div> avec le texte correspondant au titre
  On positionne la bulle au dessus de l'élément
  On ajoute une classe, pour animer l'apparition
}
 Lorsque l'on quitte le survole {
  On retire une classe, pour animer la disparition
  Lorsque l'animation se termine {
    On supprime la <div> du <body>
  }
}
 */

const Tooltip = new Vue({
    el: "#Tooltip",
    data() {
        return {
            title,
            element,
            tooltip
        }
    },

    /**
     * @param {HTMLElement} element
     */
    constructor(element) {
        this.element = element
        let tooltipTarget = this.element.getAttribute('data-tooltip')
        if (tooltipTarget) {
            this.title = document.querySelector(tooltipTarget).innerHTML
        } else {
            this.title = element.getAttribute('title')
        }
        this.tooltip = null
        this.element.addEventListener('mouseover', this.mouseOver.bind(this))
        this.element.addEventListener('mouseout', this.mouseOut.bind(this))
    },

    /**
     * Applique le système de bulle d'infos sur les éléments
     * @param {string} selector
     */
    bind(selector) {
        document.querySelectorAll(selector).forEach(element => new Tooltip(element))
    },

    mouseOver() {
        let tooltip = this.createTooltip()
        let width = tooltip.offsetWidth
        let height = tooltip.offsetHeight
        let left = this.element.offsetWidth / 2 - width / 2 + this.element.getBoundingClientRect().left + document.documentElement.scrollLeft
        let top = this.element.getBoundingClientRect().top - height - 15 + document.documentElement.scrollTop
        if (left < 20) {
            left = 20
        }
        tooltip.style.left = left + "px"
        tooltip.style.top = top + "px"
        tooltip.classList.add('visible')
    },

    mouseOut() {
        if (this.tooltip !== null) {
            this.tooltip.classList.remove('visible')
            this.tooltip.addEventListener('transitionend', () => {
                if (this.tooltip !== null) {
                    document.body.removeChild(this.tooltip)
                    this.tooltip = null
                }
            })
        }
    },

    /**
     * Crée et injecte la bulle d'info dans l'HTML
     * @returns {HTMLElement}
     */
    createTooltip() {
        if (this.tooltip === null) {
            let tooltip = document.createElement('div')
            tooltip.innerHTML = this.title
            tooltip.classList.add('ui-icon-info')
            document.body.appendChild(tooltip)
            this.tooltip = tooltip
        }
        return this.tooltip
    }
});
//FIN DE CLASSE ###############################################
