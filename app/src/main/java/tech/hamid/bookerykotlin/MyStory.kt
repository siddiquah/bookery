package tech.hamid.bookerykotlin

import java.io.Serializable

class MyStory : Serializable{
    var title: String? = null
    var story: String? = null
    var color: String? = null

    constructor() {}

    constructor(title: String, story: String, color: String) {
        this.title = title
        this.story = story
        this.color = color
    }
}
