package com.akiong.security


class MenuMap {

    static auditable = true

    Long menuId
    String url

    static constraints = {
        menuId(blank:false)
        url(blank:false)
    }

    String toString() {
      "$menuId - $url"
    }
}
