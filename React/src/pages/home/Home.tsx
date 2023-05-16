import FullCalendar from '@fullcalendar/react' // must go before plugins
import dayGridPlugin from '@fullcalendar/daygrid' // a plugin!
import { Box, Paper } from '@mui/material'

const Home = () => {
    return (
        <Box
            sx={{
                width: '80vw',
                m: '20px auto',
            }}
        >
            <Paper sx={{ p: '20px' }}>
                <FullCalendar
                    plugins={[dayGridPlugin]}
                    initialView="dayGridMonth"
                    height={'80vh'}
                    locale={'pl'}
                    weekNumberCalculation={'ISO'}
                    headerToolbar={{
                        left: 'title',
                        right: 'today prev,next',
                    }}
                    buttonText={{
                        today: 'dziś',
                    }}
                    weekNumbers={true}
                    showNonCurrentDates={false}
                    fixedWeekCount={false}
                    displayEventEnd={true}

                    
                    events={[
                        { title: 'urlop', start: '2023-05-08', end: '2023-05-12', backgroundColor: 'red' },
                        { title: 'Robota u radka', start: '2023-05-01', end: '2023-05-06', backgroundColor: 'blue' },
                        {
                            title: 'Poprawki malarskie u Stacha',
                            backgroundColor: 'green',
                            start: '2023-05-12 12:30',
                            end: '2023-05-12 15:00',
                        },
                        {
                            title: 'Papierki',
                            backgroundColor: 'yellow',
                            start: '2023-05-12 08:30',
                            end: '2023-05-12 11:30',
                        },
                        {
                            title: 'Spotkanie komitetu planowania imprez',
                            backgroundColor: 'purple',
                            start: '2023-05-12 15:00',
                            end: '2023-05-12 16:00',
                        },
                    ]}
                    // events= [
                    //     {
                    //       id: 'a',
                    //       title: 'my event',
                    //       start: '2018-09-01'
                    //     }
                    //   ]
                />
            </Paper>
        </Box>
    )
}

export default Home
