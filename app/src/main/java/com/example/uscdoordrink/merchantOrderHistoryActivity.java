package com.example.uscdoordrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class merchantOrderHistoryActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_order_history);

        drawerLayout = findViewById(R.id.drawer_layour);
    }

    public void ClickMenu(View view)
    {
        MerchantNavigationActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view)
    {
        MerchantNavigationActivity.closeDrawer(drawerLayout);
    }

    public void ClickOrderHistory(View view)
    {
        recreate();

    }
    public void ClickProfile(View view)
    {
        MerchantNavigationActivity.redirectActivity(this, ProfileActivity.class);

    }

    public void ClickEditMenu(View view)
    {
        MerchantNavigationActivity.redirectActivity(this, MerchentEditMenu.class);
    }
    public void ClickLogout(View view)
    {
        MerchantNavigationActivity.logout(this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        MerchantNavigationActivity.closeDrawer(drawerLayout);
    }

}