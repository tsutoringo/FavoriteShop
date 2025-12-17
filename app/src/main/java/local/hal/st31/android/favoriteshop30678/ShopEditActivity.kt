package local.hal.st31.android.favoriteshop30678

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import local.hal.st31.android.favoriteshop30678.data.local.DatabaseHelper
import local.hal.st31.android.favoriteshop30678.data.repository.ShopRepository
import local.hal.st31.android.favoriteshop30678.databinding.ActivityShopEditBinding

class ShopEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopEditBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var shopRepository: ShopRepository

    private var shopId: Long = 0
    private var mode = MODE_INSERT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityShopEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        databaseHelper = DatabaseHelper(this)
        shopRepository = ShopRepository(databaseHelper)

        val intent = intent

        mode = intent.getIntExtra("mode", MODE_INSERT)

        if (mode == MODE_EDIT) {
            shopId = intent.getLongExtra("shopId", 0)
            val shop = shopRepository.getById(shopId)

            if (shop != null) {
                binding.etShopName.setText(shop.name)
                binding.etTel.setText(shop.tel)
                binding.etUrl.setText(shop.url)
                binding.etMemo.setText(shop.note)
            } else {
                mode = MODE_INSERT
            }
        }

        binding.tbEdit.setNavigationOnClickListener { finish() }
        binding.tbEdit.setOnMenuItemClickListener(ToolbarMenuItemClickListener())

        if (mode == MODE_INSERT) {
            binding.tbEdit.menu.findItem(R.id.menuDelete).isVisible = false
        }
    }

    override fun onDestroy() {
        databaseHelper.close()
        super.onDestroy()
    }

    private inner class ToolbarMenuItemClickListener : Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menuSave -> {
                    saveShop()
                    true
                }
                R.id.menuDelete -> {
                    deleteShop()
                    true
                }
                else -> false
            }
        }
    }

    private fun saveShop() {
        val name = binding.etShopName.text.toString()
        val tel = binding.etTel.text.toString()
        val url = binding.etUrl.text.toString()
        val note = binding.etMemo.text.toString()

        if (name.isEmpty()) {
            binding.tilShopName.error = "店名を入力してください。"
            return
        } else {
            binding.tilShopName.error = null
        }

        val result = shopRepository.saveShop(mode, shopId, name, tel, url, note)
        if (result) {
            finish()
        }
    }

    private fun deleteShop() {
        val dialog = DeleteConfirmDialog()
        dialog.show(supportFragmentManager, "ConfirmDialog")
    }

    fun deleteShopAfterConfirm() {
        shopRepository.deleteShop(shopId)
        finish()
    }
}
