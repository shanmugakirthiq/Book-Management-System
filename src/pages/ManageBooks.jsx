import React, { useState, useEffect } from 'react';
import { 
  Button, 
  TextField, 
  Grid, 
  Typography, 
  Container, 
  Card, 
  CardContent, 
  CardActions, 
  CircularProgress, 
  Tabs, 
  Tab, 
  Box, 
  Paper, 
  Divider 
} from '@mui/material';
import Topbar from '../components/Topbar'; 
import Sidebar from '../components/Sidebar'; 

const ManageBooks = () => {
  const [books, setBooks] = useState([]);
  const [bookDetails, setBookDetails] = useState(null);
  const [newBook, setNewBook] = useState({
    name: '',
    genre: '',
    author: '',
    stock: '',
    rentPrice: '',
    publishedDate: '',
    printedDate: '',
    edition: '',
    dateAdded: ''
  });
  const [loading, setLoading] = useState(false);
  const [tabIndex, setTabIndex] = useState(0); 

  const fetchBooks = async () => {
    setLoading(true);
    try {
      const response = await fetch('/admin/books/all');
      if (!response.ok) throw new Error('Failed to fetch books');
      const data = await response.json();
      setBooks(data);
    } catch (error) {
      console.error('Error fetching books:', error);
    } finally {
      setLoading(false);
    }
  };

  const fetchLowStockBooks = async () => {
    setLoading(true);
    try {
      const response = await fetch('/admin/books/low-stock');
      if (!response.ok) throw new Error('Failed to fetch low-stock books');
      const data = await response.json();
      setBooks(data);
    } catch (error) {
      console.error('Error fetching low stock books:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddBook = async () => {
    if (!newBook.name || !newBook.genre || !newBook.author || !newBook.stock) {
      alert('Please fill all required fields!');
      return;
    }
    setLoading(true);
    try {
      const response = await fetch('/admin/books/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newBook)
      });
      if (response.ok) {
        alert('Book added successfully!');
        setNewBook({
          name: '',
          genre: '',
          author: '',
          stock: '',
          rentPrice: '',
          publishedDate: '',
          printedDate: '',
          edition: '',
          dateAdded: ''
        });
        fetchBooks();
      } else {
        alert('Failed to add book.');
      }
    } catch (error) {
      console.error('Error adding book:', error);
      alert('Error adding book.');
    } finally {
      setLoading(false);
    }
  };


  const handleViewBook = async (bookId) => {
    setLoading(true);
    try {
      const response = await fetch(`/admin/books/${bookId}`);
      if (!response.ok) throw new Error('Failed to fetch book details');
      const data = await response.json();
      setBookDetails(data);
    } catch (error) {
      console.error('Error viewing book:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateBook = async (bookId) => {
    setLoading(true);
    try {
      const response = await fetch(`/admin/books/update/${bookId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newBook)
      });
      if (response.ok) {
        alert('Book updated successfully!');
        fetchBooks();
      } else {
        alert('Failed to update book.');
      }
    } catch (error) {
      console.error('Error updating book:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDeleteBook = async (bookId) => {
    setLoading(true);
    try {
      const response = await fetch(`/admin/books/delete/${bookId}`, { method: 'DELETE' });
      if (response.ok) {
        alert('Book deleted successfully!');
        fetchBooks();
      } else {
        alert('Failed to delete book.');
      }
    } catch (error) {
      console.error('Error deleting book:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const handleTabChange = (event, newValue) => {
    setTabIndex(newValue);
  };

  return (
    <div style={{ display: 'flex', height: '100vh' }}>
      <Topbar />
      <Sidebar />
      <Container
        maxWidth="xl"
        sx={{
          ml: '240px',      
          mt: '64px',        
          p: 3,
          height: 'calc(100vh - 64px)',
          overflowY: 'auto'
        }}
      >
        <Typography variant="h4" gutterBottom>Book Management</Typography>

        <Tabs value={tabIndex} onChange={handleTabChange} aria-label="Book Management Tabs">
          <Tab label="Add Book" />
          <Tab label="List Books" />
          <Tab label="Low Stock Books" />
        </Tabs>
        
        <Box sx={{ mt: 3 }}>
          {tabIndex === 0 && (
            <Paper sx={{ p: 3, backgroundColor: '#f9f9f9' }}>
              <Typography variant="h5" gutterBottom>Add New Book</Typography>
              <Divider sx={{ mb: 3 }} />
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Book Name"
                    fullWidth
                    value={newBook.name}
                    onChange={(e) => setNewBook({ ...newBook, name: e.target.value })}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Genre"
                    fullWidth
                    value={newBook.genre}
                    onChange={(e) => setNewBook({ ...newBook, genre: e.target.value })}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Author"
                    fullWidth
                    value={newBook.author}
                    onChange={(e) => setNewBook({ ...newBook, author: e.target.value })}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Stock"
                    type="number"
                    fullWidth
                    value={newBook.stock}
                    onChange={(e) => setNewBook({ ...newBook, stock: e.target.value })}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Rent Price"
                    type="number"
                    fullWidth
                    value={newBook.rentPrice}
                    onChange={(e) => setNewBook({ ...newBook, rentPrice: e.target.value })}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Published Date"
                    type="date"
                    fullWidth
                    value={newBook.publishedDate}
                    onChange={(e) => setNewBook({ ...newBook, publishedDate: e.target.value })}
                    InputLabelProps={{ shrink: true }}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Printed Date"
                    type="date"
                    fullWidth
                    value={newBook.printedDate}
                    onChange={(e) => setNewBook({ ...newBook, printedDate: e.target.value })}
                    InputLabelProps={{ shrink: true }}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Edition"
                    fullWidth
                    value={newBook.edition}
                    onChange={(e) => setNewBook({ ...newBook, edition: e.target.value })}
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    label="Date Added"
                    type="date"
                    fullWidth
                    value={newBook.dateAdded}
                    onChange={(e) => setNewBook({ ...newBook, dateAdded: e.target.value })}
                    InputLabelProps={{ shrink: true }}
                  />
                </Grid>
                <Grid item xs={12}>
                  <Button variant="contained" onClick={handleAddBook} disabled={loading} fullWidth>
                    {loading ? 'Adding...' : 'Add Book'}
                  </Button>
                </Grid>
              </Grid>
            </Paper>
          )}

          {tabIndex === 1 && (
            <Box>
              <Grid container spacing={2}>
                {books.map((book) => (
                  <Grid item xs={12} sm={6} md={4} key={book.id}>
                    <Card sx={{ boxShadow: 3 }}>
                      <CardContent>
                        <Typography variant="h6">{book.name}</Typography>
                        <Typography variant="body2" color="textSecondary">{book.author}</Typography>
                        <Typography>Genre: {book.genre}</Typography>
                        <Typography>Stock: {book.stock}</Typography>
                        <Typography>Rent Price: ₹{book.rentPrice}</Typography>
                      </CardContent>
                      <CardActions>
                        <Button variant="outlined" onClick={() => handleViewBook(book.id)}>View</Button>
                        <Button variant="outlined" onClick={() => handleUpdateBook(book.id)}>Update</Button>
                        <Button variant="outlined" color="error" onClick={() => handleDeleteBook(book.id)}>Delete</Button>
                      </CardActions>
                    </Card>
                  </Grid>
                ))}
              </Grid>
            </Box>
          )}

          {tabIndex === 2 && (
            <Box>
              <Grid container spacing={2}>
                {books.map((book) => (
                  <Grid item xs={12} sm={6} md={4} key={book.id}>
                    <Card sx={{ boxShadow: 3 }}>
                      <CardContent>
                        <Typography variant="h6">{book.name}</Typography>
                        <Typography variant="body2" color="textSecondary">{book.author}</Typography>
                        <Typography>Genre: {book.genre}</Typography>
                        <Typography>Stock: {book.stock}</Typography>
                        <Typography>Rent Price: ₹{book.rentPrice}</Typography>
                      </CardContent>
                      <CardActions>
                        <Button variant="outlined" onClick={() => handleViewBook(book.id)}>View</Button>
                      </CardActions>
                    </Card>
                  </Grid>
                ))}
              </Grid>
            </Box>
          )}
        </Box>

        {loading && (
          <Box sx={{ display: 'flex', justifyContent: 'center', mt: 2 }}>
            <CircularProgress />
          </Box>
        )}

        {bookDetails && (
          <Paper sx={{ p: 3, mt: 3 }}>
            <Typography variant="h5">Book Details</Typography>
            <Typography>Name: {bookDetails.name}</Typography>
            <Typography>Genre: {bookDetails.genre}</Typography>
            <Typography>Author: {bookDetails.author}</Typography>
            <Typography>Stock: {bookDetails.stock}</Typography>
            <Typography>Rent Price: ₹{bookDetails.rentPrice}</Typography>
            <Typography>Published Date: {bookDetails.publishedDate}</Typography>
            <Typography>Printed Date: {bookDetails.printedDate}</Typography>
            <Typography>Edition: {bookDetails.edition}</Typography>
            <Typography>Date Added: {bookDetails.dateAdded}</Typography>
          </Paper>
        )}
      </Container>
    </div>
  );
};

export default ManageBooks;
