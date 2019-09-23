package be.fenego.store.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import be.fenego.store.clients.StoreClient;
import be.fenego.store.dtos.EnrichedStore;
import be.fenego.store.dtos.Store;
import be.fenego.store.dtos.StoreWrapper;
import be.fenego.store.entities.StoreHours;
import be.fenego.store.repositories.StoreHoursRepository;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/store")
public class StoreController {
	@Inject
	private StoreHoursRepository storeHoursRepository;
	@Inject
	protected StoreClient storeClient;

	protected String serviceUrl;

	@Value("${standard-opening-hours.opening-time}")
	List<String> openingTime;
	@Value("${standard-opening-hours.closing-time}")
	List<String> closingTime;

	@Get
	public List<EnrichedStore> getStores() {
		StoreWrapper storeWrapper = storeClient.getStores();

		Store[] stores = storeWrapper.getElements();

		List<EnrichedStore> enrichedStores = Arrays.stream(stores).map(EnrichedStore::new).collect(Collectors.toList());

		LocalDate lowerLimit = LocalDate.now().with(DayOfWeek.MONDAY);
		LocalDate upperLimit = LocalDate.now().plusWeeks(1).with(DayOfWeek.SUNDAY);

		List<StoreHours> storeHours = storeHoursRepository.findByDateRange(
				Arrays.stream(stores).map(Store::getUuid).collect(Collectors.toList()), lowerLimit, upperLimit);

		enrichedStores.forEach(x -> fillInOpeningHours(x, storeHours));

		return enrichedStores;
	}

	private EnrichedStore fillInOpeningHours(EnrichedStore enrichedStore, List<StoreHours> storeHours) {
		List<StoreHours> completeStoreHours = new ArrayList<>();
		LocalDate lowerLimit = LocalDate.now().with(DayOfWeek.MONDAY);

		for (int i = 0; i < 14; i++) {

			String standardOpeningTime = openingTime.get(i % 7);
			String standardClosingTime = closingTime.get(i % 7);

			StoreHours standard = new StoreHours(lowerLimit.plus(i, ChronoUnit.DAYS).toString(), standardOpeningTime,
					standardClosingTime);
			completeStoreHours.add(standard);
		}

		if (storeHours != null && !storeHours.isEmpty()) {
			List<String> divergentDates = storeHours.stream().map(x -> x.getDate()).collect(Collectors.toList());

			completeStoreHours.removeIf(x -> divergentDates.contains(x.getDate()));
			completeStoreHours.addAll(storeHours);
		}

		enrichedStore.setOpeningHours(completeStoreHours);

		return enrichedStore;
	}
}
