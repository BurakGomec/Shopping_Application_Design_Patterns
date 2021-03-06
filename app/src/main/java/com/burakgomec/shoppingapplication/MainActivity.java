package com.burakgomec.shoppingapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.burakgomec.shoppingapplication.Fragments.AddProductFragment;
import com.burakgomec.shoppingapplication.Fragments.HomeFragment;
import com.burakgomec.shoppingapplication.Fragments.PersonalPageFragment;
import com.burakgomec.shoppingapplication.ProductObserver.Product;
import com.burakgomec.shoppingapplication.ProductObserver.User;
import com.burakgomec.shoppingapplication.Fragments.ShoppingCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        addProducts(); //Ürünlerin calisma anında yaratılıp listeye eklendigi metot

        bottomNavigationItemSelect();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new HomeFragment()).commit(); //Vitrin fragmenti acılıyor
    }


    private void addProducts(){

        //Ürün görsellerinin dizin adresi alınıyor

        Uri xboxUri = Uri.parse("android.resource://"+getApplicationContext().getPackageName() +"/"+R.drawable.xbox);
        Uri ps5Uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.drawable.ps5);
        Uri iphone12Uri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.drawable.iphone12);
        Uri mbaUri = Uri.parse("android.resource://"+getApplicationContext().getPackageName()+"/"+R.drawable.mba);

        //Ürün görselleri dizinden alınıyor

        User ptTechnology = new User(1,"ptTech","İzmir");
        // Hardcode ilanlar var olan kullanıcıdan baska bir user nesnesine baglanıyor

        Product xbox = new Product(1,String.valueOf(xboxUri),"Xbox Series S 500 GB",ptTechnology,
                4800,"Lorem ipsum dolor sit amet, consectetur adipiscing elit");

        Product ps5 = new Product(2,String.valueOf(ps5Uri),"PS5 Digital Edition",ptTechnology
                ,8300,"500 GB SSD-2 Adet Gamepad-Ps Plus Live");

        Product iPhone12 = new Product(3,String.valueOf(iphone12Uri),"iPhone 12 Pro Max",ptTechnology
        ,16000,"Adınıza Faturalı 24 Ay Apple Garantili iPhone 12 128 GB");

        Product mba = new Product(4,String.valueOf(mbaUri),"Apple Macbook M1 Silicon",ptTechnology
                ,11500,"1 TB SSD 16 GB RAM APPLE SILICON");


        //İlanlar run-time da olusturulup urunler listesine eklenmektedir
        Product.getProductsList().add(xbox);
        Product.getProductsList().add(ps5);
        Product.getProductsList().add(iPhone12);
        Product.getProductsList().add(mba);

        //Alışveriş sepetine test amacıyla 2 adet ürün eklenmistir
        ShoppingCart.getInstance().addProductToShoppingCart(xbox);
        ShoppingCart.getInstance().addProductToShoppingCart(ps5);

    }


    private void bottomNavigationItemSelect(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.itemHome:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.itemBasket:
                        selectedFragment = new ShoppingCartFragment();
                        break;
                    case R.id.itemAddProduct:
                        selectedFragment = new AddProductFragment();
                        break;
                    case R.id.itemPersonalPage:
                        selectedFragment = new PersonalPageFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        //Uygulamanın duraklatıldıgı yasam döngüsü
        super.onPause();
        Product.getProductsList().clear();
        ShoppingCart.getInstance().getSelectedProducts().clear();
    }

}