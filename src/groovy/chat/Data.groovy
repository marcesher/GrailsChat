package chat
class Data {
    String text
    String author
    long now = new Date().time

    String toString(){
        return "{'text':'$text','author':'$author','time':'$now'}"
    }
}
