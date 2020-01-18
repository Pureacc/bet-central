import * as React from "react";
import classNames from "classnames";
import {Divider, Drawer, IconButton, withStyles} from "@material-ui/core";
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import {MainListItems, SecondaryListItems} from "./listItems";
import {compose} from "recompose";
import {connect} from "react-redux";

class LeftMenu extends React.Component {
    render() {
        const {classes, authenticated} = this.props;

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
                <MainListItems isAuthenticated={authenticated}/>
                <Divider/>
                <SecondaryListItems isAuthenticated={authenticated}/>
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

const mapStateToProps = state => ({
    authenticated: !!state.user.id,
});

export default compose(
    withStyles(styles),
    connect(mapStateToProps),
)(LeftMenu);