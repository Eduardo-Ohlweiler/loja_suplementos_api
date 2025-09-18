package com.loja_suplementos.loja_suplementos.mercadopago;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MercadoPagoService {

    public Preference create(List<PreferenceItemRequest> itemsRequest, Integer pedidoId){
        try {
            PreferenceBackUrlsRequest backUrls =
                    PreferenceBackUrlsRequest.builder()
                            .success("https://unpacifiable-ethnocentrically-marcelle.ngrok-free.app/success")
                            .pending("https://unpacifiable-ethnocentrically-marcelle.ngrok-free.app/pending")
                            .failure("https://unpacifiable-ethnocentrically-marcelle.ngrok-free.app/failure")
                            .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .backUrls(backUrls)
                    .notificationUrl("https://unpacifiable-ethnocentrically-marcelle.ngrok-free.app/api/mercadopago/webhook")
                    .externalReference(pedidoId.toString())
                    .items(itemsRequest).build();
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            return preference;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
