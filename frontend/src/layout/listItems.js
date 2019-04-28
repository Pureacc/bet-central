import React from 'react';

import {ListItem, ListItemIcon, ListItemText, ListSubheader} from '@material-ui/core'
import HomeIcon from '@material-ui/icons/Home';
import GavelIcon from '@material-ui/icons/Gavel'
import ListIcon from '@material-ui/icons/List';
import AssignmentIcon from '@material-ui/icons/Assignment';

export const mainListItems = (
  <div>
    <ListItem button>
      <ListItemIcon>
        <HomeIcon/>
      </ListItemIcon>
      <ListItemText primary="Home"/>
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <GavelIcon/>
      </ListItemIcon>
      <ListItemText primary="Calculate"/>
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <ListIcon/>
      </ListItemIcon>
      <ListItemText primary="Bets"/>
    </ListItem>
  </div>
);

export const secondaryListItems = (
  <div>
    <ListSubheader inset>Saved reports</ListSubheader>
    <ListItem button>
      <ListItemIcon>
        <AssignmentIcon/>
      </ListItemIcon>
      <ListItemText primary="Current month"/>
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <AssignmentIcon/>
      </ListItemIcon>
      <ListItemText primary="Last quarter"/>
    </ListItem>
    <ListItem button>
      <ListItemIcon>
        <AssignmentIcon/>
      </ListItemIcon>
      <ListItemText primary="Year-end sale"/>
    </ListItem>
  </div>
);