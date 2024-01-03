package app.service.performance;

// For project add 2 performance operation and compare the results based on
// Time logic in java VS Time logic in SQL
public interface PerformanceService {

    // Double salary for all users that have a name that has length < 5
    // For all his movies reservation create in db a duplicate movie with a name as the original movie and price doubled
    // Increase the address number with 1 if the user id is even
    void applyLogicOnUsers();
}
