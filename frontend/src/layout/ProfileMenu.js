import * as React from "react";
import IconButton from "@material-ui/core/IconButton";
import {AccountCircle} from "@material-ui/icons";
import {Menu} from "@material-ui/core";
import MenuItem from "@material-ui/core/MenuItem";
import {Fragment} from "react";

class ProfileMenu extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            popupAnchorEl: null,
            popupOpen: false
        };
    }

    handleOpenMenu = event => {
        this.setState({popupAnchorEl: event.currentTarget, popupOpen: true});
    };

    handleClose = () => {
        this.setState({popupOpen: false})
    };

    handleLogout = () => {
        this.props.onLoggedOut()
    };

    render() {
        return (
            <Fragment>
                <IconButton
                    aria-label="account of current user"
                    aria-controls="menu-appbar"
                    aria-haspopup="true"
                    onClick={this.handleOpenMenu}
                    color="inherit"
                >
                    <AccountCircle/>
                </IconButton>
                <Menu id="menu-appbar"
                      anchorEl={this.state.popupAnchorEl}
                      anchorOrigin={{
                          vertical: 'top',
                          horizontal: 'right',
                      }}
                      keepMounted
                      transformOrigin={{
                          vertical: 'top',
                          horizontal: 'right',
                      }}
                      open={this.state.popupOpen}
                      onClose={this.handleClose}
                >
                    <MenuItem onClick={this.handleLogout}>Log out</MenuItem>
                </Menu>
            </Fragment>
        )
    }
}

export default ProfileMenu