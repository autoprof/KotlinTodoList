package net.syntessense.app.myapplication.model

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM todoentity")
    fun getAll(): List<TodoEntity>

    @Insert
    fun add(vararg todo: TodoEntity)

    @Query(value = "DELETE FROM todoentity WHERE id = :id")
    fun remove(id: Int)

    @Query(value = "DELETE FROM todoentity")
    fun clear()

    @Query("SELECT * FROM todoentity WHERE id = :id")
    fun findById(id: Int): TodoEntity

    @Update
    fun updateTodo(vararg todos: TodoEntity)
}