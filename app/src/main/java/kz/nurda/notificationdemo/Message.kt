package kz.nurda.notificationdemo

//
// Created by  on 05.12.19.
//
class Message(
    var text: CharSequence,
    var sender: CharSequence?
) {
    var timestamp: Long = System.currentTimeMillis()
}