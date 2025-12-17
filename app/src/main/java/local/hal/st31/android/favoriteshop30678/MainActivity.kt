package local.hal.st31.android.favoriteshop30678

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.cursoradapter.widget.SimpleCursorAdapter
import local.hal.st31.android.favoriteshop30678.data.local.DatabaseHelper
import local.hal.st31.android.favoriteshop30678.data.repository.ShopRepository
import local.hal.st31.android.favoriteshop30678.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var shopRepository: ShopRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DatabaseHelper(this)
        shopRepository = ShopRepository(databaseHelper)

        binding.lvShop.onItemClickListener = ListItemClickListener()
    }

    override fun onResume() {
        super.onResume()

        binding.lvShop.adapter = SimpleCursorAdapter(
            this@MainActivity,
            android.R.layout.simple_list_item_1,
            shopRepository.findAll(),
            arrayOf("name"),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.tbMain.setOnMenuItemClickListener(ToolbarClickListener())
    }

    override fun onDestroy() {
        databaseHelper.close()
        super.onDestroy()
    }

    private inner class ToolbarClickListener: Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem): Boolean {
            var returnVal = true
            if (item.itemId == R.id.menuAdd) {
                createNew()
            } else {
                returnVal = false
            }

            return returnVal
        }
    }

    private fun createNew() {
        val intent = Intent(this@MainActivity, ShopEditActivity::class.java)
        intent.putExtra("mode", MODE_INSERT)

        startActivity(intent)
    }

    private inner class ListItemClickListener: AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val item = parent.getItemAtPosition(position) as Cursor

            val intent = Intent(this@MainActivity, ShopEditActivity::class.java)
            intent.putExtra("mode", MODE_EDIT)
            intent.putExtra("shopId", id)
//          intent.putExtra("idNo", id)
            startActivity(intent)
        }
    }
}