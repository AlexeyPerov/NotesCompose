package io.kotless.logkeeper.page

import io.kotless.dsl.lang.http.*
import io.kotless.logkeeper.utils.html
import kotlinx.html.*


@Get("/")
fun root() = html {
    head {
        title {
            +"LogKeeper"
        }

        link {
            href = "https://use.fontawesome.com/releases/v5.8.1/css/all.css"
            rel = "stylesheet"
        }
        link {
            href = "https://fonts.googleapis.com/css?family=Fira+Sans:300,400,600&display=swap"
            rel = "stylesheet"
        }
        link {
            href = ::siteCss.href
            rel = "stylesheet"
        }
    }
    body {

        div("header-block") {
            span("header-text") {
                +"LogKeeper"
            }
            span("header-muted-text") {
                +"Test API"
            }
        }
    }
}
