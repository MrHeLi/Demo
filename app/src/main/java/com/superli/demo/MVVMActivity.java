package com.superli.demo;

import android.app.Activity;
import android.os.Bundle;

import com.superli.demo.databinding.MvvmBinding;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * mvvm demo.
 *
 * @author SuperLi
 * @since 2019-12-05 23:12
 */
public class MVVMActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        binding.setViewModel(new ViewModel());
    }
}
