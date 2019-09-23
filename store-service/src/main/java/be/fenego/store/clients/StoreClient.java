package be.fenego.store.clients;

import be.fenego.store.dtos.StoreWrapper;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost/INTERSHOP/rest/WFS/inSPIRED-inTRONICS-Site/smb-responsive/stores")
public interface StoreClient {

	@Get
	public StoreWrapper getStores();
	
}
