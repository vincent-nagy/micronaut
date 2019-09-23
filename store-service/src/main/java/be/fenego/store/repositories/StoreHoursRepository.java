package be.fenego.store.repositories;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import be.fenego.store.entities.StoreHours;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.jdbc.runtime.JdbcOperations;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.spring.tx.annotation.Transactional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public abstract class StoreHoursRepository implements CrudRepository<StoreHours, Long> {
	
	private final JdbcOperations jdbcOperations;
	
	public StoreHoursRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}
	
//	@Query("SELECT id, storeuuid, date, openingtime, closingtime FROM openingtimes WHERE storeuuid IN (:storeUuids) AND date BETWEEN :startDate AND :endDate")
//	List<StoreHours> findByDateRange(List<String> storeUuids, LocalDate startDate, LocalDate endDate);
	
	@Transactional
	public List<StoreHours> findByDateRange(List<String> storeUuids, LocalDate startDate, LocalDate endDate) {
		String sql = "SELECT id, storeuuid, date, openingtime, closingtime FROM openingtimes WHERE storeuuid IN {} AND date BETWEEN to_date(?, 'YYYY-MM-DD') AND to_date(?, 'YYYY-MM-DD')";
		sql = buildQueryWithVariableArgs(sql, storeUuids.size());
		
		return jdbcOperations.<List<StoreHours>>prepareStatement(sql, statement -> {


			for(int i = 1; i <= storeUuids.size(); i++) {
				statement.setString(i, storeUuids.get(i-1));
			}
			statement.setString(storeUuids.size()+1, startDate.format(DateTimeFormatter.ISO_DATE));
			statement.setString(storeUuids.size()+2, endDate.format(DateTimeFormatter.ISO_DATE));
			
			ResultSet resultSet = statement.executeQuery();
			
			return jdbcOperations.entityStream(resultSet, StoreHours.class).collect(Collectors.toList());
		});
	}
	
	public static String buildQueryWithVariableArgs(String query, int size) {
		StringBuilder variableArgs = new StringBuilder(size);
		variableArgs.append("(");
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				variableArgs.append(',');
			}
			variableArgs.append("?");
		}
		variableArgs.append(")");

		return query.replace("{}", variableArgs.toString());
	}
}
