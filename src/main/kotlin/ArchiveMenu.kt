class ArchiveMenu : Menu<Archive>("Список архивов:") {
    private val archives = mutableListOf<Archive>()

    override val createItemText: String = "Создать архив"
    override val itemsList: List<Archive> get() = archives
    override fun getItemText(item: Any): String = (item as Archive).name
    init {
        updateMenu()
    }

    override fun onCreateItem() {
        archives.add(Archive(promptNotEmpty("Введите название архива: ")))
        updateMenu()
        println("Архив создан!")
    }

    override fun onItemSelected(index: Int) {
        NoteMenu(archives[index]).show()
        updateMenu()
    }
}