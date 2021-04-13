package net.syntessense.app.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.util.SparseBooleanArray
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.syntessense.app.myapplication.model.TodoDB
import net.syntessense.app.myapplication.model.TodoEntity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val TODOS = Room.databaseBuilder(
            applicationContext,
            TodoDB::class.java, "todo-list.db"
        ).build().todoDao()

        var todoList = arrayListOf<String>()
        var todoIdList = arrayListOf<Int>()
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, todoList)
        listView.adapter = adapter

        fun refresh() {
            val todos = TODOS.getAll()
            todoList.clear()
            todoIdList.clear()
            for (todo in todos) {
                todoList.add(todo.content)
                todoIdList.add(todo.id)
            }
            this@MainActivity.runOnUiThread(java.lang.Runnable {
                adapter.notifyDataSetChanged()
            })
        }

        GlobalScope.launch {
            refresh()
        }

        add.setOnClickListener {
            val txt = editText.text.toString();
            editText.text.clear()
            GlobalScope.launch{
                TODOS.add(TodoEntity(0, txt))
                refresh()
            }
        }

        clear.setOnClickListener {
            GlobalScope.launch {
                TODOS.clear()
                refresh()
            }
        }

        delete.setOnClickListener {
            GlobalScope.launch {
                val position: SparseBooleanArray = listView.checkedItemPositions
                val count = listView.count
                var i = count - 1
                while (i >= 0) {
                    if (position.get(i)) {
                        TODOS.remove(todoIdList[i])
                    }
                    i--
                }
                position.clear()
                refresh()
            }
        }

        listView.setOnItemClickListener { adapterView, view, i, l ->
            android.widget.Toast.makeText(
                    this,
                    "You Selected the item --> " + todoIdList[i].toString(), android.widget.Toast.LENGTH_SHORT
            ).show()
        }

    }
}