package tickticket.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import tickticket.dao.EventTypeRepository;
import tickticket.dao.ProfileRepository;
import tickticket.dto.ProfileDTO;
import tickticket.model.Event;
import tickticket.model.EventType;
import tickticket.model.Profile;
import tickticket.model.User;
import tickticket.service.ProfileService;
import tickticket.service.UserService;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class ID021AddTypePreferenceToUserProfile {

    @Mock
    private UserService userService;

    @Mock
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

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
        List<UUID> eventIDs = new ArrayList<>();
        eventIDs.add(TYPE_ID_1);
        eventIDs.add(TYPE_ID_2);
        eventIDs.add(TYPE_ID_3);


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

            return profile;

        });

        lenient().when(eventTypeRepository.findAllById(eventIDs)).thenAnswer((InvocationOnMock invocation) -> {
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
        });



            lenient().when(eventTypeRepository.findById(any(UUID.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TYPE_ID_1)) {

                EventType eventType1 = new EventType();
                eventType1.setId(TYPE_ID_1);
                eventType1.setName(TYPE_NAME_1);
                eventType1.setDescription(TYPE_DESCRIPTION_1);

                return Optional.of(eventType1);

            }else if(invocation.getArgument(0).equals(TYPE_ID_2)){
                EventType eventType2 = new EventType();
                eventType2.setId(TYPE_ID_2);
                eventType2.setName(TYPE_NAME_2);
                eventType2.setDescription(TYPE_DESCRIPTION_2);

                return Optional.of(eventType2);
            }else if(invocation.getArgument(0).equals(TYPE_ID_3)){
                EventType eventType2 = new EventType();
                eventType2.setId(TYPE_ID_3);
                eventType2.setName(TYPE_NAME_3);
                eventType2.setDescription(TYPE_DESCRIPTION_3);

                return Optional.of(eventType2);
            }
            else {
                return Optional.empty();
            }
        });
        Answer<?> returnParameterAsAnswer = (InvocationOnMock invocation) -> invocation.getArgument(0);
        lenient().when(profileRepository.save(any(Profile.class))).thenAnswer(returnParameterAsAnswer);


    }

    @Test
    public void testAdd1TypePreferenceToUserProfile() {

        Profile profile = null;

        EventType eventType1 = new EventType();
        eventType1.setId(TYPE_ID_1);
        eventType1.setName(TYPE_NAME_1);
        eventType1.setDescription(TYPE_DESCRIPTION_1);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(PROFILE_ID);
        profileDTO.setFirstName(FIRST_NAME);
        profileDTO.setLastName(LAST_NAME);
        profileDTO.setEmail(EMAIL);
        profileDTO.setPhoneNumber(PHONE_NUMBER);
        profileDTO.setAddress(ADDRESS);
        profileDTO.setProfilePicture(PICTURE);
        profileDTO.setDateOfBirth(BIRTH_DATE);
        ArrayList<EventType> interests = new ArrayList<>();
        interests.add(eventType1);

        try{
            profile = profileService.addEventTypePreference(profileDTO);
        }catch(Exception e){
            fail();
        }

        assertNotNull(profile);
        assertEquals(interests, profile.getInterests());

    }

    @Test
    public void testAdd3TypsePreferenceToUserProfile() {

        Profile profile = null;

        EventType eventType1 = new EventType();
        eventType1.setId(TYPE_ID_1);
        eventType1.setName(TYPE_NAME_1);
        eventType1.setDescription(TYPE_DESCRIPTION_1);

        EventType eventType2 = new EventType();
        eventType2.setId(TYPE_ID_2);
        eventType2.setName(TYPE_NAME_2);
        eventType2.setDescription(TYPE_DESCRIPTION_2);

        EventType eventType3 = new EventType();
        eventType3.setId(TYPE_ID_3);
        eventType3.setName(TYPE_NAME_3);
        eventType3.setDescription(TYPE_DESCRIPTION_3);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(PROFILE_ID);
        profileDTO.setFirstName(FIRST_NAME);
        profileDTO.setLastName(LAST_NAME);
        profileDTO.setEmail(EMAIL);
        profileDTO.setPhoneNumber(PHONE_NUMBER);
        profileDTO.setAddress(ADDRESS);
        profileDTO.setProfilePicture(PICTURE);
        profileDTO.setDateOfBirth(BIRTH_DATE);
        ArrayList<EventType> interests = new ArrayList<>();
        interests.add(eventType1);
        interests.add(eventType2);
        interests.add(eventType3);

        try{
            profile = profileService.addEventTypePreference(profileDTO);
        }catch(Exception e){
            fail();
        }

        assertNotNull(profile);
        assertEquals(interests, profile.getInterests());

    }

    @Test
    public void testNoPreferences(){
        Profile profile = null;

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(PROFILE_ID);
        profileDTO.setFirstName(FIRST_NAME);
        profileDTO.setLastName(LAST_NAME);
        profileDTO.setEmail(EMAIL);
        profileDTO.setPhoneNumber(PHONE_NUMBER);
        profileDTO.setAddress(ADDRESS);
        profileDTO.setProfilePicture(PICTURE);
        profileDTO.setDateOfBirth(BIRTH_DATE);
        ArrayList<EventType> interests = new ArrayList<>();

        try{
            profile = profileService.addEventTypePreference(profileDTO);
        }catch(Exception e){
            fail();
        }

        assertNotNull(profile);
        assertEquals(interests, profile.getInterests());
        assertEquals(0, profile.getInterests().size());
    }










}
