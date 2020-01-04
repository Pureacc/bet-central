import * as React from "react";
import classNames from "classnames";
import {Divider, Drawer, IconButton, List, withStyles} from "@material-ui/core";
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import {mainListItems, secondaryListItems} from "./listItems";

class LeftMenu extends React.Component {
    render() {
        const {classes} = this.props;

        return (
            <Drawer
                variant="permanent"
                classes={{
                    paper: classNames(classes.drawerPaper, !this.props.open && classes.drawerPaperClose),
                }}
                open={this.props.open}
            >
                <div className={classes.toolbarIcon}>
                    <IconButton onClick={this.props.onDrawerClose}>
                        <ChevronLeftIcon/>
                    </IconButton>
                </div>
                <Divider/>
                <List>{mainListItems}</List>
                <Divider/>
                <List>{secondaryListItems}</List>
            </Drawer>
        )
    }
}

const drawerWidth = 240;

const styles = theme => ({
    toolbarIcon: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: '0 8px',
        ...theme.mixins.toolbar,
    },
    drawerPaper: {
        position: 'relative',
        whiteSpace: 'nowrap',
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    drawerPaperClose: {
        overflowX: 'hidden',
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        width: theme.spacing(7),
        [theme.breakpoints.up('sm')]: {
            width: theme.spacing(9),
        },
    },
});

export default withStyles(styles)(LeftMenu);