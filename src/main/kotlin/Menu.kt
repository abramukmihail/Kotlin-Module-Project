import java.util.Scanner

abstract class Menu<T>(protected val title: String) {
    protected val items = mutableListOf<Pair<String, () -> Unit>>()
    protected val scanner = Scanner(System.`in`)
    protected open val createItemText: String get() = ""
    protected abstract val itemsList: List<Any>
    protected abstract fun getItemText(item: Any): String
    protected open fun onCreateItem() {}
    protected open fun onItemSelected(index: Int) {}
    protected fun addItem(description: String, action: () -> Unit) {
        items.add(description to action)
    }
    protected fun updateMenu() {
        items.clear()
        if (createItemText.isNotEmpty()) {
            addItem(createItemText) { onCreateItem() }
        }
        updateItems()
    }
    protected open fun updateItems() {
        itemsList.forEachIndexed { i, item ->
            addItem(getItemText(item)) { onItemSelected(i) }
        }
    }

    open fun show() {
        while (true) {
            println(title)
            items.forEachIndexed { index, (desc, _) -> println("$index. $desc") }
            println("${items.size}. Выход")
            print("Выберите пункт меню: ")

            val input = scanner.nextLine()

            when {
                input.isBlank() -> {
                    println("\nОшибка: Ввод не может быть пустым!")
                }
                !input.matches(Regex("\\d+")) -> {
                    println("\nОшибка: Необходимо ввести число от 0 до ${items.size}")
                    println("Вы ввели: '$input'")
                }
                else -> {
                    val choice = input.toInt()
                    when {
                        choice == items.size -> return
                        choice in items.indices -> items[choice].second()
                        else -> println("\nОшибка: Нет пункта с номером $choice. Введите от 0 до ${items.size}")
                    }
                }
            }
            println()
        }
    }

    protected fun promptNotEmpty(prompt: String): String {
        while (true) {
            print(prompt)
            scanner.nextLine().trim().takeIf { it.isNotEmpty() }?.let { return it }
            println("Поле не может быть пустым!")
        }
    }
}