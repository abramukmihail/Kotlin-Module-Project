class NoteMenu(private val archive: Archive) : Menu<Note>("\nАрхив: ${archive.name}\nСписок заметок:") {
    override val createItemText: String = "Создать заметку"
    override val itemsList: List<Note> get() = archive.notes
    override fun getItemText(item: Any): String = (item as Note).title
    init {
        updateMenu()
    }

    override fun onCreateItem() {
        archive.notes.add(Note(
            title = promptNotEmpty("Введите название заметки: "),
            content = promptNotEmpty("Введите текст заметки: ")
        ))
        updateMenu()
        println("Заметка создана!")
    }

    override fun onItemSelected(index: Int) {
        with(archive.notes[index]) {
            println("\nЗаметка: $title \n$content\n")
            println("Для возврата нажмите Enter")
            readlnOrNull()
        }
    }
}