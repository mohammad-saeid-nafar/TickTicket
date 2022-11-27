import React from "react";
import { useEffect, useState } from "react";
import {
    Box,
    Card,
    CardContent,
    CardHeader,
    IconButton,
    Typography,
    Button,
    TextField, Alert
} from "@mui/material";
import {
    MoreVert as MoreVertIcon,
} from "@mui/icons-material";
import axios from "axios";
import Stack from "@mui/material/Stack";

const ProfileCard = (props) => {
    const [anchorEl, setAnchorEl] = useState(null);
    const actionsOpen = Boolean(anchorEl);
    const [edit, setEdit] = useState(false);

    let profileId = props.user.profile.id;
    const [firstName, setFirstName] = React.useState(props.user.profile.firstName);
    const [lastName, setLastName] = React.useState(props.user.profile.lastName);
    const [email, setEmail] = React.useState(props.user.profile.email);
    const [address, setAddress] = React.useState(props.user.profile.address);
    const [phoneNumber, setPhoneNumber] = React.useState(props.user.profile.phoneNumber);
    const [dob, setDob] = React.useState(props.user.profile.dateOfBirth);
    const [error, setError] = React.useState(false);
    const [errorMessage, setErrorMessage] = React.useState("");
    const [success, setSuccess] = React.useState(false);
    const [successMessage, setSuccessMessage] = React.useState("");


    const handleFirstNameChange = (event) => {
        setError(false);
        setFirstName(event.target.value);
    };

    const handleLastNameChange = (event) => {
        setError(false);
        setLastName(event.target.value);
    };

    const handleEmailChange = (event) => {
        setError(false);
        setEmail(event.target.value);
    };

    const handleAddressChange = (event) => {
        setError(false);
        setAddress(event.target.value);
    };

    const handlePhoneNumberChange = (event) => {
        setError(false);
        setPhoneNumber(event.target.value);
    };

    const handleDobChange = (event) => {
        setError(false);
        setDob(event.target.value);
    };

    const handleEditProfile = React.useCallback(() => {
        axios.patch("profiles", {
            id: profileId,
            firstName: firstName,
            lastName: lastName,
            email: email,
            address: address,
            phoneNumber: parseInt(phoneNumber),
            dateOfBirth: dob,
        }).then(res => {
            setSuccess(true);
            setSuccessMessage("Profile Updated successfully, please refresh to view your updated information");
            setEdit(false);
        }).catch(function (error) {
            setError(true);
            setErrorMessage("Wrong inputs, profile was not updated.");
        });
    }, [profileId, firstName, lastName, email, phoneNumber, address, dob]);

    useEffect(() => {
        console.log("userId: " + props.user.id);
        // eslint-disable-next-line
    }, []);

    const handleActionsClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    if(!edit) {
        return (
            <Card>
                <CardHeader
                    action={
                        <div>
                            {
                                props.user.id !== localStorage.getItem("userId") && (
                                    <IconButton
                                        id="basic-button"
                                        aria-controls={actionsOpen ? "basic-menu" : undefined}
                                        aria-haspopup="true"
                                        aria-expanded={actionsOpen ? "true" : undefined}
                                        onClick={handleActionsClick}
                                    >
                                        <MoreVertIcon/>
                                    </IconButton>
                                )}
                            <Button
                                onClick={() => {
                                    setEdit(true);
                                }}
                            >
                                Edit
                            </Button>
                        </div>
                    }

                    title={
                        <Box
                            sx={{
                                display: "flex",
                            }}
                        >
                            <Typography>{props.user.username}</Typography>
                        </Box>
                    }
                    subheader={
                        props.user.profile.firstName + " " + props.user.profile.lastName
                    }
                />
                <CardContent>
                    <Typography variant="body2" color="text.secondary">
                        {"Email: " + props.user.profile.email} <br/>
                        {"Phone Number: " + props.user.profile.phoneNumber} <br/>
                        {"Adress: " + props.user.profile.address} <br/>
                        {"Date Of Birth: " + props.user.profile.dateOfBirth}
                    </Typography>
                </CardContent>
            </Card>
        );
    } else {
        return (
            <Card>
                <CardHeader
                    action={
                        <div>
                            {
                                props.user.id !== localStorage.getItem("userId") && (
                                    <IconButton
                                        id="basic-button"
                                        aria-controls={actionsOpen ? "basic-menu" : undefined}
                                        aria-haspopup="true"
                                        aria-expanded={actionsOpen ? "true" : undefined}
                                        onClick={handleActionsClick}
                                    >
                                        <MoreVertIcon/>
                                    </IconButton>
                                )}
                            <Button
                                onClick={() => {
                                    setEdit(false);
                                }}
                            >
                                Cancel
                            </Button>
                        </div>
                    }

                    title={
                        <Box
                            sx={{
                                display: "flex",
                                justifyContent:"center"
                            }}
                        >
                            <Typography>Edit {props.user.username}'s profile</Typography>
                        </Box>
                    }

                />
                <CardContent>
                    <div style={{ display: 'flex', justifyContent: 'center' }} >
                        <div style={{ marginTop:20 }}>
                            <TextField label="First Name" variant="standard" defaultValue={props.user.profile.firstName} onChange={handleFirstNameChange}/>
                            <TextField label="Last Name" variant="standard" defaultValue={props.user.profile.lastName} onChange={handleLastNameChange}/>
                        </div>
                        <div style={{ marginTop:20 }}>
                            <TextField label="Email" variant="standard" defaultValue={props.user.profile.email} onChange={handleEmailChange}/>
                            <TextField label="Phone Number" variant="standard" defaultValue={props.user.profile.phoneNumber} onChange={handlePhoneNumberChange}/>
                        </div>
                        <div style={{ marginTop:20 }}>
                            <TextField label="Address" variant="standard" defaultValue={props.user.profile.address} onChange={handleAddressChange}/>
                            <TextField label="Date Of Birth (yyyy-mm-dd)" variant="standard" defaultValue={props.user.profile.dateOfBirth} onChange={handleDobChange}/>
                        </div>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                        <Button onClick={handleEditProfile}>Save</Button>
                    </div>
                </CardContent>
                <Stack sx={{ width: '100%' }} spacing={2}>
                    {error && <Alert severity="error">{errorMessage}</Alert>}
                    {success && <Alert severity="success">{successMessage}</Alert>}
                </Stack>
            </Card>
        )
    }
};
export default ProfileCard;
