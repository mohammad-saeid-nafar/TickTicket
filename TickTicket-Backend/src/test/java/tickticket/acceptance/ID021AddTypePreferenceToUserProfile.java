package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import tickticket.dao.ProfileRepository;
import tickticket.dto.ProfileDTO;
import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.service.EventTypeService;
import tickticket.service.ProfileService;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID021AddTypePreferenceToUserProfile {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EventTypeService eventTypeService;

    @InjectMocks
    private ProfileService profileService;

    // Data for User
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USERNAME = "user1";
    private static final String PASSWORD = "Password123";
    private static final LocalDate CREATED = LocalDate.of(2022,01,13);

    //Data for User Profile
    private static final UUID PROFILE_ID = UUID.randomUUID();
    private static final String FIRST_NAME = "name1";
    private static final String LAST_NAME = "famName";
    private static final String EMAIL = "name1@mail.com";
    private static final String PHONE_NUMBER = "5141110000";
    private static final String ADDRESS = "address1";
    private static final LocalDate BIRTH_DATE = LocalDate.of(2001,02,19);
    private static final String PICTURE = "profile_pic_1";

    //Data for Event Type 1
    private static final UUID TYPE_ID_1 = UUID.randomUUID();
    private static final String TYPE_NAME_1 = "Pop Music";
    private static final String TYPE_DESCRIPTION_1 = "Music";

    //Data for Event Type 2
    private static final UUID TYPE_ID_2 = UUID.randomUUID();
    private static final String TYPE_NAME_2 = "Street Food";
    private static final String TYPE_DESCRIPTION_2 = "Food";

    //Data for Event Type 3
    private static final UUID TYPE_ID_3 = UUID.randomUUID();
    private static final String TYPE_NAME_3 = "Comedy";
    private static final String TYPE_DESCRIPTION_3 = "Comedy";

    @BeforeEach
    public void setUp() {

        lenient().when(profileRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            Profile profile = new Profile();
            profile.setId(PROFILE_ID);
            profile.setFirstName(FIRST_NAME);
            profile.setLastName(LAST_NAME);
            profile.setEmail(EMAIL);
            profile.setPhoneNumber(PHONE_NUMBER);
            profile.setAddress(ADDRESS);
            profile.setDateOfBirth(BIRTH_DATE);
            profile.setProfilePicture(PICTURE);

            return Optional.of(profile);

        });

        lenient().when(eventTypeService.getAllEventTypes(any())).thenAnswer((InvocationOnMock invocation) -> {
            if(((List) invocation.getArgument(0)).size() == 1){
                List<EventType> eventTypes = new ArrayList<>();
                EventType eventType1 = new EventType();
                eventType1.setId(TYPE_ID_1);
                eventType1.setName(TYPE_NAME_1);
                eventType1.setDescription(TYPE_DESCRIPTION_1);
                eventTypes.add(eventType1);
                return eventTypes;
            } else if(((List) invocation.getArgument(0)).size() == 3) {
                List<EventType> eventTypes = new ArrayList<>();
                EventType eventType1 = new EventType();
                eventType1.setId(TYPE_ID_1);
                eventType1.setName(TYPE_NAME_1);
                eventType1.setDescription(TYPE_DESCRIPTION_1);
                eventTypes.add(eventType1);

                EventType eventType2 = new EventType();
                eventType2.setId(TYPE_ID_2);
                eventType2.setName(TYPE_NAME_2);
                eventType2.setDescription(TYPE_DESCRIPTION_2);
                eventTypes.add(eventType2);

                EventType eventType3 = new EventType();
                eventType3.setId(TYPE_ID_3);
                eventType3.setName(TYPE_NAME_3);
                eventType3.setDescription(TYPE_DESCRIPTION_3);
                eventTypes.add(eventType3);

                return eventTypes;
            }else{
                return new ArrayList<>();
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(profileRepository.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);


    }

    @Test
    public void testAdd1TypePreferenceToUserProfile() {

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(PROFILE_ID);
        profileDTO.setInterestIds(Collections.singletonList(TYPE_ID_1));

        try{
            Profile profile = profileService.addEventTypePreference(profileDTO);
            assertNotNull(profile);
            assertEquals(1, profile.getInterests().size());
            assertEquals(TYPE_ID_1, profile.getInterests().get(0).getId());
        }catch(Exception e){
            fail();
        }

    }

    @Test
    public void testAdd3TypePreferenceToUserProfile() {

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(PROFILE_ID);
        profileDTO.setInterestIds(Arrays.asList(TYPE_ID_1, TYPE_ID_2, TYPE_ID_3));

        try{
            Profile profile = profileService.addEventTypePreference(profileDTO);
            assertNotNull(profile);
            assertEquals(3, profile.getInterests().size());
            assertEquals(TYPE_ID_1, profile.getInterests().get(0).getId());
            assertEquals(TYPE_ID_2, profile.getInterests().get(1).getId());
            assertEquals(TYPE_ID_3, profile.getInterests().get(2).getId());
        }catch(Exception e){
            fail();
        }
    }

    @Test
    public void testNoPreferences(){
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(PROFILE_ID);
        profileDTO.setInterestIds(new ArrayList<>());
        try{
            Profile profile = profileService.addEventTypePreference(profileDTO);
            assertNotNull(profile);
            assertEquals(0, profile.getInterests().size());
        }catch(Exception e){
            fail();
        }
    }










}
