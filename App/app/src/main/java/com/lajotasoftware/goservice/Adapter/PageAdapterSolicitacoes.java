package com.lajotasoftware.goservice.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lajotasoftware.goservice.Fragments.SolicitacoesEnviadas;
import com.lajotasoftware.goservice.Fragments.SolicitacoesRecebidas;

public class PageAdapterSolicitacoes extends FragmentStateAdapter {

    public PageAdapterSolicitacoes(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new SolicitacoesRecebidas();
            case 1:
                return new SolicitacoesEnviadas();
            default:
                return new SolicitacoesRecebidas();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
