/*
 * Name: John Starich
 * EID: js68634
 */

import java.util.*;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
	/**
	 * Determines whether a candidate Matching represents a solution to the
	 * Stable Matching problem. Study the description of a Matching in the
	 * project documentation to help you with this.
	 *
	 * @complexity O (tenants * apartments * (tenants + landlords))
	 *             Given that landlords <= tenants, tighter bound = O(tenants^2 * apartments)
	 *             Going further, if we assume that tenants == apartments, tigher bound = O(tenants^3)
	 */
	public boolean isStableMatching(Matching match) {
		// stable matching = both tenant and landlord don't prefer each other from different matchings

		// for every tenant, make sure there isn't an unstable match
		// O(tenants)
		for(int tenant = 0; tenant < match.getTenantCount(); tenant++) {
			int apartment = match.getTenantMatching().get(tenant); // O(1) current matched apartment
			int apartmentPref = match.getTenantPref().get(tenant).get(apartment); // O(1) current tenant's thoughts on this apartment

			// build a collection of apartments that the tenant prefers to the current apartment
			Vector<Integer> apartmentsTenantPrefersToCurrent = new Vector<>(); // O(1)
			Vector<Integer> tenantPreferences = match.getTenantPref().get(tenant); // O(1)
			// O(apartments)
			for(int apartmentIndex = 0; apartmentIndex < tenantPreferences.size(); apartmentIndex++) {
				if(apartmentIndex != apartment && tenantPreferences.get(apartmentIndex) < apartmentPref) { // O(1) higher preference (lower #)
					apartmentsTenantPrefersToCurrent.add(apartmentIndex); // O(1)
				}
			}

			// search through these preferred apartments for a landlord that also prefers the current tenant
			// O(apartments)
			for(int preferredApartment : apartmentsTenantPrefersToCurrent) {
				int landlord;
				// find the landlord for this preferred apartment
				// O(landlords)
				for(landlord = 0; landlord < match.getLandlordCount(); landlord++) {
					if(match.getLandlordOwners().get(landlord).contains(preferredApartment)) { // O(1)
						break;
					}
				}

				// find out if this tenant would prefer this tenant over the currently matched tenant
				Vector<Integer> landlordPrefs = match.getLandlordPref().get(landlord); // O(1)
				int landlordPreferenceForTenant = landlordPrefs.get(tenant); // O(1)
				int currentTenant;
				// O(tenants)
				for(currentTenant = 0; currentTenant < match.getTenantMatching().size(); currentTenant++) {
					if(match.getTenantMatching().get(currentTenant) == preferredApartment) break; // O(1)
				}
				int landlordPreference = landlordPrefs.get(currentTenant); // O(1)
				if (landlordPreferenceForTenant < landlordPreference) { // O(1)
					return false;
				}
			}
		}
		return true;
	}

	/*
		while(a tenant is free and that tenant has not proposed to all apartment options) { // "apartment options" or "landlords"?
			for(preferredApartment : tenantPrefs) {
				if(preferredApartment is available) {
					matches[tenant] = preferredApartment;
					break;
				}
				if(currentLandownerOfPreferredApartment prefers new tenant) {
					matches[tenant] = preferredTenant;
					matches[otherTenant] = null;
					break;
				}
			}
		}
		return new Matching(match, matches);
	 */

	/*
	initially all men and women are free
	while there is some man who is free and hasn't proposed to all women:
		m = choose that free man
		w = choose first woman in man's pref list that has not been proposed to yet
		if w is free:
			m and w become matched
		else:
			m2 = man w is matched with already
			if w prefers m2 to m:
				m remains free
			else:
				m and w become matched
				m2 becomes free
	return matches
	 */

	/**
	 * Determines a solution to the Stable Matching problem from the given input
	 * set. Study the project description to understand the variables which
	 * represent the input to your solution.
	 *
	 * @complexity O (tenants * (tenants + apartments + landlords))
	 *             but we know apartments >= tenants >= landlords
	 *             so tighter upper-bound is O(tenants * apartments).
	 *             If we assume apartments == tenants, then O(tenants^2)
	 * @param match the current matching problems information
	 * @return A stable Matching.
	 */
	public Matching stableMatchingGaleShapley(Matching match) {
		final int tenantCount = match.getTenantCount(); // O(1)
		final int landlordCount = match.getLandlordCount(); // O(1)
		int apartmentCount = 0; // O(1)
		// O(landlords)
		for(int landlord = 0; landlord < landlordCount; landlord++) {
			apartmentCount += match.getLandlordOwners().get(landlord).size(); // O(1)
		}
		Map<Integer, Integer> matchings = new HashMap<>(tenantCount); // O(1)
		Map<Integer, Set<Integer>> proposalsLeft = new HashMap<>(tenantCount); // O(1)
		// O(tenants)
		for(int tenant = 0; tenant < tenantCount; tenant++) {
			Set<Integer> unproposedApartments = new HashSet<>(apartmentCount); // O(1)
			// O(apartments)
			for(int apartment = 0; apartment < apartmentCount; apartment++) unproposedApartments.add(apartment); // O(1)
			proposalsLeft.put(tenant, unproposedApartments); // O(1)
		}

		Integer tenant;
		// O(tenants * (tenants + landlords + apartments))
		while ((tenant = freeTenant(matchings, tenantCount)) != null && ! proposalsLeft.get(tenant).isEmpty()) {
			// O(apartments) and decreases by 1 each iteration per tenant
			Integer unproposedApartment = firstUnproposedApartment(match.getTenantPref().get(tenant), proposalsLeft.get(tenant));
			if (! matchings.values().contains(unproposedApartment)) { // O(1)
				matchings.put(tenant, unproposedApartment); // O(1)
			}
			else {
				// O(tenants)
				Integer currentTenantOfApartment = residentOfApartment(unproposedApartment, matchings);
				// O(landlords)
				Integer landlord = landlordForApartment(unproposedApartment, match.getLandlordOwners());
				Vector<Integer> landlordPrefsOfTenants = match.getLandlordPref().get(landlord); // O(1)
				Integer landlordPrefOfCurrentTenant = landlordPrefsOfTenants.get(currentTenantOfApartment); // O(1)
				Integer landlordPrefOfNewTenant = landlordPrefsOfTenants.get(tenant); // O(1)
				if (landlordPrefOfCurrentTenant >= landlordPrefOfNewTenant) { // O(1)
					matchings.remove(currentTenantOfApartment); // O(1)
					matchings.put(tenant, unproposedApartment); // O(1)
				}
				// else remains free, just remove this apartment from their proposalsLeft
			}
			proposalsLeft.get(tenant).remove(unproposedApartment); // O(1)
		}
		match.setTenantMatching(convertMatchings(matchings)); // O(1)
		return match;
	}

	/**
	 * Finds the current tenant matched to the provided apartment
	 * @complexity O(matchings)
	 * @param apartment the provided apartment
	 * @param matchings the current matchings of tenants to their apartments
	 * @return the current tenant matched to the provided apartment
	 */
	private Integer residentOfApartment(Integer apartment, Map<Integer, Integer> matchings) {
		// O(matchings)
		for(Map.Entry<Integer, Integer> matching : matchings.entrySet()) {
			if(apartment.equals(matching.getValue())) return matching.getKey(); // O(1)
		}
		return null;
	}

	/**
	 * Finds the landlord for the provided apartment
	 * @complexity O(landlords)
	 * @param apartment the apartment to find a landlord for
	 * @param landlordOwnership the list of landlords and the apartments they own
	 * @return the landlord that owns the provided apartment
	 */
	private Integer landlordForApartment(Integer apartment, Vector<Vector<Integer>> landlordOwnership) {
		// O(landlordOwnership)
		for(int landlord = 0; landlord < landlordOwnership.size(); landlord++) {
			if(landlordOwnership.get(landlord).contains(apartment)) return landlord; // O(1)
		}
		return null;
	}

	/**
	 * Finds the best apartment in the given preference list that has not been proposed to.
	 * Assumes that there is still an apartment not yet proposed to.
	 * @complexity O(proposalsLeft)
	 * @param preferences the tenant's preference list
	 * @param proposalsLeft the proposals the tenant has not yet made
	 * @return the first apartment that the tenant hasn't proposed to but prefers the most
	 */
	private Integer firstUnproposedApartment(Vector<Integer> preferences, Set<Integer> proposalsLeft) {
		Integer lowestPref = Integer.MAX_VALUE; // O(1)
		Integer lowestPrefApartment = -1; // = highest priority apartment
		// O(|proposalsLeft|)
		for(Integer apartment : proposalsLeft) {
			if (preferences.get(apartment) < lowestPref) { // O(1)
				lowestPref = preferences.get(apartment); // O(1)
				lowestPrefApartment = apartment; // O(1)
			}
		}
		return lowestPrefApartment;
	}

	/**
	 * Converts the map of matchings into the expected Vector of integers
	 * @complexity O(matchings)
	 * @param matchings the map of matchings
	 * @return the expected Vector of integers, where index = tenant and value = apartment
	 */
	public Vector<Integer> convertMatchings(Map<Integer, Integer> matchings) {
		Vector<Integer> newMatchings = new Vector<>(matchings.size()); // O(1)
		newMatchings.addAll(matchings.values()); // O(|matchings|)
		// O(|matchings|)
		for(Map.Entry<Integer, Integer> entry : matchings.entrySet()) {
			newMatchings.set(entry.getKey(), entry.getValue()); // O(1)
		}
		return newMatchings;
	}

	/**
	 * Find the first tenant that isn't matched to an apartment.
	 * @complexity O(totalTenants)
	 * @param matchings The current pairs of tenants to their apartments
	 * @param totalTenants The total number of tenants such that tenants = range(0, totalTenants)
	 * @return The number of a tenant that is not matched, null if all tenants are matched
	 */
	public Integer freeTenant(Map<Integer, Integer> matchings, int totalTenants) {
		// O(totalTenants)
		for(int tenant = 0; tenant < totalTenants; tenant++) {
			if(! matchings.containsKey(tenant)) return tenant; // O(1) due to hashCodes in HashMaps
		}
		return null;
	}
}
