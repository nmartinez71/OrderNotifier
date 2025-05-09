import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.ordernotifier.data.OrderInfo

@Dao
interface OrderDao {
    @Insert suspend fun insertOrder(order: OrderInfo)

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<OrderInfo>

    @Delete
    suspend fun deleteOrder(order: OrderInfo)

    @Query("DELETE FROM orders")
    suspend fun deleteAll()
}

