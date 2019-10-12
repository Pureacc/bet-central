import React from 'react';
import {BrowserRouter, Route} from 'react-router-dom'
import classNames from 'classnames';
import {CssBaseline, Divider, Drawer, IconButton, List, withStyles} from '@material-ui/core'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import {mainListItems, secondaryListItems} from './listItems';
import SimpleTable from '../Bets';
import Calculate from "../Calculate";
import Home from "../Home";
import Register from "../Register";
import SignIn from "../SignIn";
import TopMenu from "../menu/TopMenu";

class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: true,
            userId: null
        }
    }

    handleLoggedIn = (userId) => {
        this.setState({userId: userId});
    };

    handleLoggedOut = () => {
        this.setState({userId: null});
    };

    handleDrawerOpen = () => {
        this.setState({open: true});
    };

    handleDrawerClose = () => {
        this.setState({open: false});
    };

    render() {
        const {classes} = this.props;

        return (
            <div className={classes.root}>
                <CssBaseline/>

                <BrowserRouter>
                    <TopMenu open={this.state.open}
                             authenticated={this.state.userId}
                             onDrawerOpen={this.handleDrawerOpen}/>
                    <Drawer
                        variant="permanent"
                        classes={{
                            paper: classNames(classes.drawerPaper, !this.state.open && classes.drawerPaperClose),
                        }}
                        open={this.state.open}
                    >
                        <div className={classes.toolbarIcon}>
                            <IconButton onClick={this.handleDrawerClose}>
                                <ChevronLeftIcon/>
                            </IconButton>
                        </div>
                        <Divider/>
                        <List>{mainListItems}</List>
                        <Divider/>
                        <List>{secondaryListItems}</List>
                    </Drawer>

                    <main className={classes.content}>
                        <div className={classes.appBarSpacer}/>
                        <Route exact path="/" component={Home}/>
                        <Route path="/register" component={Register}/>
                        <Route path="/signin"
                               render={(props) => <SignIn onLoggedIn={this.handleLoggedIn} {...props} />}/>
                        <Route path="/calculate" component={Calculate}/>
                        <Route path="/bets" render={() =>
                            <SimpleTable/>
                        }/>
                    </main>

                </BrowserRouter>
            </div>
        );
    }
}

const drawerWidth = 240;

const styles = theme => ({
    root: {
        display: 'flex',
    },
    toolbar: {
        paddingRight: 24, // keep right padding when drawer closed
    },
    toolbarIcon: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: '0 8px',
        ...theme.mixins.toolbar,
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
    },
    appBarShift: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    menuButton: {
        marginLeft: 12,
        marginRight: 36,
    },
    menuButtonHidden: {
        display: 'none',
    },
    title: {
        flexGrow: 1,
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
        width: theme.spacing.unit * 7,
        [theme.breakpoints.up('sm')]: {
            width: theme.spacing.unit * 9,
        },
    },
    appBarSpacer: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
        height: '100vh',
        overflow: 'auto',
    },
    chartContainer: {
        marginLeft: -22,
    },
    h5: {
        marginBottom: theme.spacing.unit * 2,
    },
});

export default withStyles(styles)(Dashboard);