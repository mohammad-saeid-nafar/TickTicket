import React from "react";
import { useEffect } from "react";
// import axios from "axios";
import {
    Box,
    Card,
    CardContent,
    CardHeader,
    IconButton,
    Typography,
    // Button
} from "@mui/material";
import {
    MoreVert as MoreVertIcon,
} from "@mui/icons-material";

const ProfileCard = (props) => {
    const [anchorEl, setAnchorEl] = React.useState(null);
    const actionsOpen = Boolean(anchorEl);
    // const [edit, setEdit] = useState(false);


    useEffect(() => {
        console.log("userIdddd: " + props.key);
        console.log("userIdddd: " + props.event.id);
    }, []);


    const handleActionsClick = (event) => {
        setAnchorEl(event.currentTarget);
    };

    return (
        <Card>
            <CardHeader
                action={
                    <div>
                        {
                        props.event.id !== localStorage.getItem("userId")  && (
                            <IconButton
                                id="basic-button"
                                aria-controls={actionsOpen ? "basic-menu" : undefined}
                                aria-haspopup="true"
                                aria-expanded={actionsOpen ? "true" : undefined}
                                onClick={handleActionsClick}
                            >
                                <MoreVertIcon />
                            </IconButton>
                        )}
                        {/*<Button*/}
                        {/*    onClick={() => {*/}
                        {/*        // setEdit(true);*/}
                        {/*        this.setState({ editing: true });*/}

                        {/*    }}*/}
                        {/*>*/}
                        {/*    Edit*/}
                        {/*</Button>*/}
                    </div>
                }
                title={
                    <Box
                        sx={{
                            display: "flex",
                        }}
                    >
                        <Typography>{props.event.username}</Typography>
                    </Box>
                }
                subheader={props.event.profile.firstName + " " + props.event.profile.lastName}

            />
            <CardContent>
                <Typography variant="body2" color="text.secondary">
                    {"Email: " + props.event.profile.email} <br />
                    {"Phone Number: " + props.event.profile.phoneNumber} <br />
                    {"Adress: " + props.event.profile.address} <br />
                    {"Date Of Birth: " + props.event.profile.dateOfBirth}


                </Typography>
            </CardContent>
        </Card>
    );
};
export default ProfileCard;
